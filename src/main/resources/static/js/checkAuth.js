function login() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const msgBox = document.getElementById("message");

    msgBox.innerHTML = '';
    msgBox.style.display = 'none';
    msgBox.classList.remove("error", "success");

    if (!username || !password) {
        msgBox.innerHTML = '⚠️ Usuário e senha são obrigatórios.';
        msgBox.classList.add("error");
        msgBox.style.display = 'block';
        return;
    }

    fetch("/api", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            nome: username,
            senha: password
        })
    })
    .then(res => {
        if (res.ok) {
            return res;
        }

        return res.text().then(text => {
            throw new Error(text || "Erro ao fazer login. Tente novamente.");
        });
    })
    .then(() => {
        const expirationTime = Date.now() + 2 * 60 * 1000;

        localStorage.setItem('loginStatus', JSON.stringify({
            loggedIn: true,
            expiresAt: expirationTime
        }));

        msgBox.innerHTML = '✅ Login realizado com sucesso! Redirecionando...';
        msgBox.classList.add("success");
        msgBox.style.display = 'block';
        window.location.href = "home.html";
    })
    .catch(err => {
        localStorage.removeItem('loginStatus');

        msgBox.innerHTML = `❌ Falha no login.`;
        msgBox.classList.add("error");
        msgBox.style.display = 'block';

        setTimeout(() => {
            window.location.href = "login.html";
        }, 10000);
    });
}

function checkAuth() {
    const statusString = localStorage.getItem('loginStatus');

    if (!statusString) {
        window.location.href = 'login.html';
        return;
    }

    const loginData = JSON.parse(statusString);
    const currentTime = Date.now();

    if (currentTime > loginData.expiresAt) {

        localStorage.removeItem('loginStatus');
        alert("Sua sessão expirou. Faça login novamente.");
        window.location.href = 'login.html';
    }
}

function logoff(){
    window.location.href = 'login.html';
}
