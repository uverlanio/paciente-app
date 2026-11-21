document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("login-form");
    const usernameField = document.getElementById("username");
    const passwordField = document.getElementById("password");
    const message = document.getElementById("message");
    const submitButton = document.getElementById("botao-entrar");

    form.addEventListener("submit", async event => {
        event.preventDefault();
        message.textContent = "";
        message.className = "feedback";

        const nome = usernameField.value.trim();
        const senha = passwordField.value.trim();
        if (!nome || !senha) {
            showMessage("Preencha seu usuário e sua senha.", "error");
            return;
        }

        submitButton.disabled = true;
        try {
            const response = await fetch("/api", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nome, senha })
            });
            if (!response.ok) {
                throw new Error("Não foi possível entrar. Confira seus dados e tente novamente.");
            }

            const usuario = await response.json();
            localStorage.setItem("loginStatus", JSON.stringify({
                loggedIn: true,
                nome: usuario.nome,
                expiresAt: Date.now() + (8 * 60 * 60 * 1000)
            }));
            window.location.href = "home.html";
        } catch (error) {
            showMessage(error.message || "Não foi possível conectar. Tente novamente.", "error");
        } finally {
            submitButton.disabled = false;
        }
    });

    function showMessage(text, type) {
        message.textContent = text;
        message.classList.add(type);
    }
});
