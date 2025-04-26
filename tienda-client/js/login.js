import { login, getAccessToken } from './auth.js';

const token = getAccessToken();
if (token) {
    window.location.href = "pedidos.html";
}

document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    login(username, password);  // Llamamos al método login desde auth.js
});