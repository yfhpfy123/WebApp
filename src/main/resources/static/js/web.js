async function getAllUsers(){
    const response = await fetch('/api/all');
    const users = await response.json();
    return users
}
async function getCurrentUser() {
    const response = await fetch('/api/auth');
    const user = await response.json();
    return user
}
async function getRoles() {
    const response = await fetch('/api/roles');
    const roles = await response.json();
    return roles
}

async function saveUser(user) {
    const response = await fetch('/api/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    const result = await response.json();
    return result
}

async function deleteUser(user) {
    const response = await fetch('/api/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    const result = await response.json();
    return result
}

async function updateUser(user) {
    const response = await fetch('/api/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
    const result = await response.json();
    return result
}

const authUsername = document.getElementById('auth-username');
authUsername.innerText = getCurrentUser().username
const authRoles = document.getElementById('auth-roles');
authRoles.innerText = getCurrentUser().roles
