const API_BASE = '/api/v1';
let currentUser = JSON.parse(localStorage.getItem('user')) || null;
let currentToken = localStorage.getItem('token') || null;

// DOM Elements
const authSection = document.getElementById('authSection');
const dashboardSection = document.getElementById('dashboardSection');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const toggleAuth = document.getElementById('toggleAuth');
const authTitle = document.getElementById('authTitle');
const authSubtitle = document.getElementById('authSubtitle');
const toggleText = document.getElementById('toggleText');

// Init application
document.addEventListener('DOMContentLoaded', () => {
    if (currentToken && currentUser) {
        showDashboard();
    }
});

// Auth Toggle Logic
toggleAuth.addEventListener('click', (e) => {
    e.preventDefault();
    if (loginForm.style.display !== 'none') {
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
        authTitle.innerText = 'Join TFConnect';
        authSubtitle.innerText = 'Create your secure trade identity';
        toggleText.innerText = 'Already have an account?';
        toggleAuth.innerText = 'Login Now';
    } else {
        loginForm.style.display = 'block';
        registerForm.style.display = 'none';
        authTitle.innerText = 'Welcome Back';
        authSubtitle.innerText = 'Securely access your trade portal';
        toggleText.innerText = "Don't have an account?";
        toggleAuth.innerText = 'Register Now';
    }
});

// Login Handler
loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(`${API_BASE}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) throw new Error('Invalid credentials');

        const data = await response.json();
        saveAuth(data);
        showDashboard();
    } catch (err) {
        alert(err.message);
    }
});

// Register Handler
registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const orgType = document.querySelector('input[name="orgType"]:checked').value;

    const payload = {
        username: document.getElementById('regUsername').value,
        email: document.getElementById('regEmail').value,
        password: document.getElementById('regPassword').value,
        roles: [document.getElementById('regRole').value],
    };

    if (orgType === 'existing') {
        payload.organizationId = parseInt(document.getElementById('regOrgId').value);
    } else {
        payload.organizationName = document.getElementById('regOrgName').value;
        payload.organizationRegNumber = document.getElementById('regOrgRegNo').value;
        payload.organizationAddress = document.getElementById('regOrgAddress').value;
    }

    try {
        const response = await fetch(`${API_BASE}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const error = await response.text();
            throw new Error(error || 'Registration failed');
        }

        const data = await response.json();
        saveAuth(data);
        alert('Account created successfully with New Organization!');
        showDashboard();
    } catch (err) {
        alert(err.message);
    }
});

function toggleOrgInput(type) {
    const idGroup = document.getElementById('orgIdGroup');
    const newGroup = document.getElementById('newOrgGroup');
    if (type === 'existing') {
        idGroup.style.display = 'block';
        newGroup.style.display = 'none';
        document.getElementById('regOrgId').required = true;
        document.getElementById('regOrgName').required = false;
    } else {
        idGroup.style.display = 'none';
        newGroup.style.display = 'block';
        document.getElementById('regOrgId').required = false;
        document.getElementById('regOrgName').required = true;
    }
}

function saveAuth(data) {
    currentToken = data.token;
    currentUser = {
        username: data.username,
        roles: data.roles,
        organizationName: data.organizationName
    };
    localStorage.setItem('token', currentToken);
    localStorage.setItem('user', JSON.stringify(currentUser));
}

function logout() {
    localStorage.clear();
    location.reload();
}

function showDashboard() {
    authSection.style.display = 'none';
    dashboardSection.style.display = 'block';

    document.getElementById('userNameDisplay').innerText = currentUser.username;
    document.getElementById('orgDisplay').innerText = `Organization: ${currentUser.organizationName}`;

    const role = currentUser.roles[0];
    const badge = document.getElementById('userRoleBadge');
    badge.innerText = role.replace('ROLE_CORPORATE_', '').replace('ROLE_', '');
    badge.className = 'role-badge ' + 'badge-' + role.toLowerCase().replace('role_corporate_', '').replace('role_', '');

    // Show/Hide Initiation button for Maker
    if (role === 'ROLE_CORPORATE_MAKER') {
        document.getElementById('btnInitiate').style.display = 'block';
    }

    if (role === 'ROLE_BANK_ADMIN') {
        document.getElementById('pageTitle').innerText = 'Bank Master Dashboard';
        document.getElementById('orgDisplay').innerText = 'System Overview';
    }

    fetchTrades();
    fetchStats();
}

async function fetchStats() {
    try {
        const response = await fetch(`${API_BASE}/trade/dashboard-stats`, {
            headers: { 'Authorization': `Bearer ${currentToken}` }
        });
        if (!response.ok) return;
        const stats = await response.json();

        document.getElementById('statTotal').innerText = stats.totalActiveTrades;
        document.getElementById('statPending').innerText = stats.pendingMyAction;
        document.getElementById('statApproved').innerText = stats.approvedToday;
    } catch (err) {
        console.error('Stats fetch error:', err);
    }
}

async function fetchTrades() {
    try {
        // We fetch "pending" for active work, but for dashboard we might want all.
        // For now, let's satisfy the role-based viewing.
        let url = `${API_BASE}/trade/pending`;

        const response = await fetch(url, {
            headers: { 'Authorization': `Bearer ${currentToken}` }
        });

        if (!response.ok) return;

        const trades = await response.json();
        renderTrades(trades);
    } catch (err) {
        console.error(err);
    }
}

function renderTrades(trades) {
    const tbody = document.getElementById('tradeTableBody');
    tbody.innerHTML = '';

    if (trades.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; color: #71717a; padding: 3rem;">No pending transactions found for your role.</td></tr>';
        return;
    }

    trades.forEach(trade => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td style="font-family: monospace; color: #ff004c;">${trade.referenceNumber}</td>
            <td>${trade.type.replace(/_/g, ' ')}</td>
            <td style="font-weight: 600;">${trade.amount.toLocaleString()} ${trade.currency}</td>
            <td>${trade.maker ? trade.maker.username : 'SYSTEM'}</td>
            <td><span class="status-pill status-${trade.status.toLowerCase()}">${trade.status}</span></td>
            <td>
                ${renderActionButtons(trade)}
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function renderActionButtons(trade) {
    const role = currentUser.roles[0];
    if (role === 'ROLE_CORPORATE_CHECKER' && trade.status === 'SUBMITTED') {
        return `<button class="action-btn" onclick="openRemarksPopup(${trade.id}, 'VERIFY')">Verify</button>`;
    }
    if (role === 'ROLE_CORPORATE_AUTHORIZER' && trade.status === 'PENDING_APPROVAL') {
        return `<button class="action-btn" onclick="openRemarksPopup(${trade.id}, 'AUTHORIZE')">Approve</button>`;
    }
    return `<button class="action-btn" onclick="viewDetails(${trade.id})">Details</button>`;
}

function viewDetails(id) {
    // For now, just show a simple alert or it could open a read-only modal
    alert("Transaction tracking ID: " + id + "\nThis feature is under development to show full audit history.");
}

function updateStats(trades) {
    document.getElementById('statTotal').innerText = trades.length;
    document.getElementById('statPending').innerText = trades.length; // Simplified for MVP
}

// Trade Initiation
async function showInitiateForm() {
    document.getElementById('overlay').style.display = 'block';
    document.getElementById('initiateForm').style.display = 'block';
}

function hideInitiateForm() {
    document.getElementById('overlay').style.display = 'none';
    document.getElementById('initiateForm').style.display = 'none';
}

document.getElementById('tradeForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const payload = {
        type: document.getElementById('tradeType').value,
        amount: document.getElementById('tradeAmount').value,
        currency: document.getElementById('tradeCurrency').value,
        remarks: document.getElementById('tradeRemarks').value
    };

    try {
        const response = await fetch(`${API_BASE}/trade/initiate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${currentToken}`
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) throw new Error('Failed to initiate trade');

        alert('Trade initiated successfully!');
        hideInitiateForm();
        fetchTrades();
        fetchStats();
    } catch (err) {
        alert(err.message);
    }
});

// Remarks & Action Logic
let currentActionTradeId = null;
let currentActionType = null;

function openRemarksPopup(id, type) {
    currentActionTradeId = id;
    currentActionType = type;
    document.getElementById('overlay').style.display = 'block';
    document.getElementById('remarksPopup').style.display = 'block';
    document.getElementById('remarksTitle').innerText = (type === 'VERIFY' ? 'Verify' : 'Authorize') + ' Transaction';
}

function hideRemarksPopup() {
    document.getElementById('overlay').style.display = 'none';
    document.getElementById('remarksPopup').style.display = 'none';
}

document.getElementById('btnApproveAction').addEventListener('click', () => submitAction(true));
document.getElementById('btnRejectAction').addEventListener('click', () => submitAction(false));

async function submitAction(isApprove) {
    const remarks = document.getElementById('actionRemarks').value;
    if (!remarks) return alert('Please enter remarks');

    const endpoint = isApprove
        ? (currentActionType === 'VERIFY' ? 'check' : 'authorize')
        : 'reject';

    try {
        const response = await fetch(`${API_BASE}/trade/${currentActionTradeId}/${endpoint}?remarks=${encodeURIComponent(remarks)}`, {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${currentToken}` }
        });

        if (!response.ok) throw new Error('Action failed');

        alert(isApprove ? 'Transaction Approved!' : 'Transaction Rejected!');
        hideRemarksPopup();
        fetchTrades();
        fetchStats();
    } catch (err) {
        alert(err.message);
    }
}
