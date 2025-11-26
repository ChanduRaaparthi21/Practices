const API_URL = 'http://localhost:8080';

// Auth Helpers
const setToken = (token) => localStorage.setItem('jwt_token', token);
const getToken = () => localStorage.getItem('jwt_token');
const removeToken = () => localStorage.removeItem('jwt_token');
const isAuthenticated = () => !!getToken();

const getAuthHeaders = () => ({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${getToken()}`
});

// Navigation
const navigateTo = (path) => window.location.href = path;

// Logout
const logout = () => {
    removeToken();
    navigateTo('/index.html');
};

// Check Auth on Protected Pages
const checkAuth = () => {
    if (!isAuthenticated()) {
        navigateTo('/index.html');
    }
};

// API Calls
const api = {
    login: async (username, password) => {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        if (!response.ok) throw new Error('Login failed');
        return response.json();
    },

    register: async (username, password, role) => {
        const response = await fetch(`${API_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password, role })
        });
        if (!response.ok) throw new Error('Registration failed');
        return response.json();
    },

    // Admin
    createQuestion: async (question) => {
        const response = await fetch(`${API_URL}/admin/question`, {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(question)
        });
        return response.json();
    },

    createQuiz: async (quiz) => {
        const response = await fetch(`${API_URL}/quiz`, {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(quiz)
        });
        return response.json();
    },

    getAllQuizzes: async () => {
        const response = await fetch(`${API_URL}/quizzes`, {
            headers: getAuthHeaders()
        });
        return response.json();
    },

    getAllQuestions: async () => {
        const response = await fetch(`${API_URL}/questions`, {
            headers: getAuthHeaders()
        });
        return response.json();
    },

    // User
    getQuiz: async (id) => {
        const response = await fetch(`${API_URL}/quiz/${id}`, {
            headers: getAuthHeaders()
        });
        return response.json();
    },

    submitQuiz: async (id, answers) => {
        const response = await fetch(`${API_URL}/quiz/${id}/submit`, {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(answers)
        });
        return response.json();
    },

    getUserScores: async () => {
        const response = await fetch(`${API_URL}/user/scores`, {
            headers: getAuthHeaders()
        });
        return response.json();
    }
};

// UI Helpers
const showError = (message) => {
    alert(message); // Simple alert for now, can be improved
};
