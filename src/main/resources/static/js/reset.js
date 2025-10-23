function reset() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("newpassword").value.trim();
    const msgBox = document.getElementById("message");

    msgBox.innerHTML = '';
    msgBox.style.display = 'none';
    msgBox.classList.remove("error", "success");

    if (!username || !password) {
        msgBox.innerHTML = '⚠️ Usuário e a nova senha são obrigatórios.';
        msgBox.classList.add("error");
        msgBox.style.display = 'block';
        return;
    }

    fetch("/api/reset", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            nome: username,
            senha: '',
            novaSenha: password
        })
    })
    .then(res => {
        if (res.ok) {
            return res;
        }

        return res.text().then(text => {
            throw new Error(text || "Erro ao tentar alterar a senha. Tente novamente.");
        });
    })
    .then(() => {

        msgBox.innerHTML = '✅ Senha atualizada com sucesso! Redirecionando...';
        msgBox.classList.add("success");
        msgBox.style.display = 'block';

        localStorage.removeItem('loginStatus');
        window.location.href = 'login.html';

    })
    .catch(err => {

        msgBox.innerHTML = `❌ Não foi possível alterar a senha.`;
        msgBox.classList.add("error");
        msgBox.style.display = 'block';

        setTimeout(() => {
            window.location.href = "home.html";
        }, 10000);
    });
}

document.addEventListener('DOMContentLoaded', (event) => {

    const campoDeEntrada = document.getElementById('newpassword');
    const botaoEntrar = document.getElementById('botao-entrar');

    username.addEventListener('keyup', (e) => {
        if (e.key === 'Enter' || e.key === 13) {
            e.preventDefault();
            botaoEntrar.focus();
        }
    });
});