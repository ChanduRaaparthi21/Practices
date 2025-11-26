// API Configuration
const API_BASE_URL = 'http://localhost:8080/api';
let authToken = localStorage.getItem('token');

// API Helper Functions
async function apiCall(endpoint, options = {}) {
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };

    if (authToken && !options.skipAuth) {
        headers['Authorization'] = `Bearer ${authToken}`;
    }

    const config = {
        ...options,
        headers
    };

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);

        if (response.status === 401) {
            logout();
            return null;
        }

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Request failed');
        }

        // Handle file downloads
        if (options.download) {
            return response.blob();
        }

        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        }

        return await response.text();
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}

// Authentication
document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    const errorDiv = document.getElementById('loginError');

    try {
        const data = await apiCall('/auth/login', {
            method: 'POST',
            body: JSON.stringify({ username, password }),
            skipAuth: true
        });

        authToken = data.token;
        localStorage.setItem('token', authToken);
        localStorage.setItem('username', data.username);

        showApp();
    } catch (error) {
        errorDiv.textContent = 'Login failed. Please check your credentials.';
        errorDiv.style.display = 'block';
    }
});

document.getElementById('logoutBtn')?.addEventListener('click', logout);

function logout() {
    authToken = null;
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    showLogin();
}

function showLogin() {
    document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
    document.getElementById('loginPage').classList.add('active');
    document.querySelector('.navbar').style.display = 'none';
}

function showApp() {
    document.querySelector('.navbar').style.display = 'block';
    showPage('exchange-sets');
}

// Page Navigation
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', (e) => {
        e.preventDefault();
        const page = e.target.dataset.page;
        showPage(page);
    });
});

function showPage(pageName) {
    // Update active nav link
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
        if (link.dataset.page === pageName) {
            link.classList.add('active');
        }
    });

    // Show page
    document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
    document.getElementById(`${pageName}Page`).classList.add('active');

    // Load page data
    switch (pageName) {
        case 'settings':
            loadWorkspace();
            break;
        case 'resources':
            loadResources();
            break;
        case 'exchange-sets':
            loadExchangeSets();
            break;
        case 'audit-logs':
            loadAuditLogs();
            break;
    }
}

// Workspace
document.getElementById('workspaceForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const path = document.getElementById('workspacePath').value;
    const messageDiv = document.getElementById('settingsMessage');

    try {
        await apiCall('/workspace', {
            method: 'POST',
            body: JSON.stringify({ path })
        });

        showMessage(messageDiv, 'Workspace configured successfully!', 'success');
        loadWorkspace();
    } catch (error) {
        showMessage(messageDiv, error.message, 'danger');
    }
});

async function loadWorkspace() {
    try {
        const data = await apiCall('/workspace');
        if (data) {
            document.getElementById('workspacePath').value = data.path;
        }
    } catch (error) {
        console.log('No workspace configured');
    }
}

// Resources
async function loadResources() {
    const loading = document.getElementById('resourcesLoading');
    const empty = document.getElementById('resourcesEmpty');
    const table = document.getElementById('resourcesTable');
    const tbody = document.getElementById('resourcesTableBody');

    loading.style.display = 'block';
    empty.style.display = 'none';
    table.style.display = 'none';

    try {
        const resources = await apiCall('/resources');
        loading.style.display = 'none';

        if (!resources || resources.length === 0) {
            empty.style.display = 'block';
            return;
        }

        tbody.innerHTML = resources.map(r => `
            <tr>
                <td>${r.fileName}</td>
                <td><span class="badge ${getResourceTypeBadge(r.fileType)}">${r.fileType.replace('_', ' ')}</span></td>
                <td>${new Date(r.uploadedAt).toLocaleString()}</td>
                <td>
                    <button class="btn btn-danger btn-sm" onclick="deleteResource(${r.id})">Delete</button>
                </td>
            </tr>
        `).join('');

        table.style.display = 'table';
    } catch (error) {
        loading.style.display = 'none';
        showMessage(document.getElementById('resourcesMessage'), error.message, 'danger');
    }
}

function getResourceTypeBadge(type) {
    switch (type) {
        case 'PRIVATE_KEY': return 'bg-danger';
        case 'CERTIFICATE': return 'bg-success';
        case 'PUBLIC_KEY': return 'bg-info';
        default: return 'bg-secondary';
    }
}

function showAddResourceModal() {
    document.getElementById('addResourceModal').classList.add('active');
}

document.getElementById('addResourceForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append('file', document.getElementById('resourceFile').files[0]);
    formData.append('type', document.getElementById('resourceType').value);

    try {
        await fetch(`${API_BASE_URL}/resources`, {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${authToken}` },
            body: formData
        });

        closeModal('addResourceModal');
        showMessage(document.getElementById('resourcesMessage'), 'Resource uploaded successfully!', 'success');
        loadResources();
        e.target.reset();
    } catch (error) {
        showMessage(document.getElementById('resourcesMessage'), error.message, 'danger');
    }
});

async function deleteResource(id) {
    if (!confirm('Are you sure you want to delete this resource?')) return;

    try {
        await apiCall(`/resources/${id}`, { method: 'DELETE' });
        showMessage(document.getElementById('resourcesMessage'), 'Resource deleted successfully!', 'success');
        loadResources();
    } catch (error) {
        showMessage(document.getElementById('resourcesMessage'), error.message, 'danger');
    }
}

// Exchange Sets
async function loadExchangeSets() {
    const loading = document.getElementById('exchangeSetsLoading');
    const empty = document.getElementById('exchangeSetsEmpty');
    const table = document.getElementById('exchangeSetsTable');
    const tbody = document.getElementById('exchangeSetsTableBody');

    loading.style.display = 'block';
    empty.style.display = 'none';
    table.style.display = 'none';

    try {
        const exchangeSets = await apiCall('/exchange-sets');
        loading.style.display = 'none';

        if (!exchangeSets || exchangeSets.length === 0) {
            empty.style.display = 'block';
            return;
        }

        tbody.innerHTML = exchangeSets.map(es => `
            <tr>
                <td>${es.name}</td>
                <td><span class="badge ${es.locked ? 'bg-warning' : 'bg-success'}">${es.locked ? '🔒 Locked' : '🔓 Unlocked'}</span></td>
                <td>${new Date(es.createdAt).toLocaleString()}</td>
                <td>
                    <div class="action-buttons">
                        <button class="btn btn-success btn-sm" onclick="signExchangeSet(${es.id})" ${es.locked ? 'disabled' : ''}>Sign</button>
                        <button class="btn btn-info btn-sm" onclick="packageExchangeSet(${es.id})" ${es.locked ? 'disabled' : ''}>Package</button>
                        ${es.locked ?
                `<button class="btn btn-warning btn-sm" onclick="unlockExchangeSet(${es.id})">Unlock</button>` :
                `<button class="btn btn-secondary btn-sm" onclick="lockExchangeSet(${es.id})">Lock</button>`
            }
                        <button class="btn btn-danger btn-sm" onclick="deleteExchangeSet(${es.id})" ${es.locked ? 'disabled' : ''}>Delete</button>
                    </div>
                </td>
            </tr>
        `).join('');

        table.style.display = 'table';
    } catch (error) {
        loading.style.display = 'none';
        showMessage(document.getElementById('exchangeSetsMessage'), error.message, 'danger');
    }
}

function showCreateExchangeSetModal() {
    document.getElementById('createExchangeSetModal').classList.add('active');
}

document.getElementById('createExchangeSetForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('exchangeSetName').value;

    try {
        await apiCall('/exchange-sets', {
            method: 'POST',
            body: JSON.stringify({ name })
        });

        closeModal('createExchangeSetModal');
        showMessage(document.getElementById('exchangeSetsMessage'), 'Exchange Set created successfully!', 'success');
        loadExchangeSets();
        e.target.reset();
    } catch (error) {
        showMessage(document.getElementById('exchangeSetsMessage'), error.message, 'danger');
    }
});

async function lockExchangeSet(id) {
    try {
        await apiCall(`/exchange-sets/${id}/lock`, { method: 'POST' });
        showMessage(document.getElementById('exchangeSetsMessage'), 'Exchange Set locked!', 'success');
        loadExchangeSets();
    } catch (error) {
        showMessage(document.getElementById('exchangeSetsMessage'), error.message, 'danger');
    }
}

async function unlockExchangeSet(id) {
    try {
        await apiCall(`/exchange-sets/${id}/unlock`, { method: 'POST' });
        showMessage(document.getElementById('exchangeSetsMessage'), 'Exchange Set unlocked!', 'success');
        loadExchangeSets();
    } catch (error) {
        showMessage(document.getElementById('exchangeSetsMessage'), error.message, 'danger');
    }
}

async function signExchangeSet(id) {
    const privateKeyId = prompt('Enter Private Key ID:');
    if (!privateKeyId) return;

    try {
        await apiCall(`/exchange-sets/${id}/sign?privateKeyId=${privateKeyId}`, { method: 'POST' });
        showMessage(document.getElementById('exchangeSetsMessage'), 'Exchange Set signed successfully!', 'success');
    } catch (error) {
        showMessage(document.getElementById('exchangeSetsMessage'), error.message, 'danger');
    }
}

async function packageExchangeSet(id) {
    try {
        const blob = await apiCall(`/exchange-sets/${id}/package`, {
            method: 'POST',
            download: true
        });

        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `exchange-set-${id}.zip`;
        a.click();

        showMessage(document.getElementById('exchangeSetsMessage'), 'Exchange Set packaged successfully!', 'success');
    } catch (error) {
        showMessage(document.getElementById('exchangeSetsMessage'), error.message, 'danger');
    }
}

async function deleteExchangeSet(id) {
    if (!confirm('Are you sure you want to delete this exchange set?')) return;

    try {
        await apiCall(`/exchange-sets/${id}`, { method: 'DELETE' });
        showMessage(document.getElementById('exchangeSetsMessage'), 'Exchange Set deleted successfully!', 'success');
        loadExchangeSets();
    } catch (error) {
        showMessage(document.getElementById('exchangeSetsMessage'), error.message, 'danger');
    }
}

// Audit Logs
async function loadAuditLogs() {
    const loading = document.getElementById('auditLogsLoading');
    const tbody = document.getElementById('auditLogsTableBody');

    loading.style.display = 'block';

    try {
        const data = await apiCall('/audit-logs?page=0&size=50');
        loading.style.display = 'none';

        const logs = data.content || [];
        tbody.innerHTML = logs.map(log => `
            <tr>
                <td>${new Date(log.timestamp).toLocaleString()}</td>
                <td><span class="badge ${getActionBadge(log.action)}">${log.action.replace(/_/g, ' ')}</span></td>
                <td>${log.details}</td>
            </tr>
        `).join('');
    } catch (error) {
        loading.style.display = 'none';
        console.error('Failed to load audit logs:', error);
    }
}

function getActionBadge(action) {
    if (action.includes('CREATED')) return 'bg-success';
    if (action.includes('DELETED')) return 'bg-danger';
    if (action.includes('SIGNED')) return 'bg-info';
    if (action.includes('PACKAGED')) return 'bg-warning';
    return 'bg-secondary';
}

// Modal Functions
function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('active');
}

// Utility Functions
function showMessage(element, message, type) {
    element.className = `alert alert-${type}`;
    element.textContent = message;
    element.style.display = 'block';
    setTimeout(() => {
        element.style.display = 'none';
    }, 5000);
}

// Initialize
if (authToken) {
    showApp();
} else {
    showLogin();
}
