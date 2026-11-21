document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("reset-form");
    const usernameField = document.getElementById("username");
    const passwordField = document.getElementById("newpassword");
    const message = document.getElementById("message");
    const submitButton = document.getElementById("botao-enviar");

    form.addEventListener("submit", async event => {
        event.preventDefault();
        message.textContent = "";
        message.className = "feedback";

        const nome = usernameField.value.trim();
        const novaSenha = passwordField.value.trim();
        if (!nome || !novaSenha) {
            showMessage("Preencha seu usuário e sua nova senha.", "error");
            return;
        }

        submitButton.disabled = true;
        try {
            const response = await fetch("/api/reset", {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nome, senha: "", novaSenha })
            });
            if (!response.ok) {
                throw new Error("Não foi possível redefinir a senha. Confira os dados e tente novamente.");
            }

            showMessage("Senha atualizada com sucesso. Você já pode entrar com a nova senha.", "success");
            form.reset();
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
