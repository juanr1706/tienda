// ./js/auth.js

export function getAccessToken() {
    return localStorage.getItem("access_token");
}

export function getRefreshToken() {
    return localStorage.getItem("refresh_token");
}

export function getUsername() {
    const token = getAccessToken();
    if (token) {
        try {
            const decodedToken = JSON.parse(atob(token.split('.')[1])); // Decodificar el token JWT
            return decodedToken.sub; // El nombre de usuario se suele guardar en 'sub'
        } catch (error) {
            console.error('Error al decodificar el token', error);
            return null;
        }
    }
    return null;
}

export async function validateTokenOrRedirect() {
    const token = getAccessToken();

    if (!token) {
        window.location.href = "login.html";  // Redirigir al login si no hay token
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/protected", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error("Token inválido");
        }

        return true;
    } catch (error) {
        logout();  // Si hay un error con el token, hacer logout
    }
}

export function logout() {
    const token = getAccessToken();

    if (token) {
        fetch("http://localhost:8080/auth/logout", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }).finally(() => {
            localStorage.removeItem("access_token");
            localStorage.removeItem("refresh_token");
            window.location.href = "login.html";  // Redirigir al login después de hacer logout
        });
    } else {
        window.location.href = "login.html";  // Redirigir al login si no hay token
    }
}

export async function login(username, password) {
    const loginRequest = {
        username: username,
        password: password
    };

    try {
        const response = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(loginRequest)
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem("access_token", data.access_token);
            localStorage.setItem("refresh_token", data.refresh_token);
            window.location.href = "pedidos.html";
        } else {
            alert("Error en el login. Intenta de nuevo.");
        }
    } catch (error) {
        alert("Ocurrió un error al intentar iniciar sesión. " + error.message);
    }
}
