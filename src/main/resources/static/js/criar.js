async function criar() {
    const nomeField = document.getElementById("nomeNovo");
    const prontuarioField = document.getElementById("prontuarioNovo");
    const msgBox = document.getElementById("resultado");
    const botaoSalvar = document.getElementById("botao-salvar");
    const nome = nomeField.value.trim();
    const prontuario = prontuarioField.value.trim();

    mostrarResultado(msgBox, "");
    if (!nome || !prontuario) {
        mostrarResultado(msgBox, "Nome e prontuário são obrigatórios.", "error");
        return;
    }

    botaoSalvar.disabled = true;
    try {
        const response = await fetch("/api/pacientes", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome, prontuario })
        });
        if (!response.ok) {
            const detalhe = await response.text();
            throw new Error(detalhe || "Não foi possível criar o prontuário.");
        }

        const paciente = await response.json();
        mostrarResultado(msgBox, `Prontuário de ${paciente.nome} criado com sucesso.`, "success");
        nomeField.value = "";
        prontuarioField.value = "";
    } catch (error) {
        mostrarResultado(msgBox, error.message || "Não foi possível conectar. Tente novamente.", "error");
    } finally {
        botaoSalvar.disabled = false;
    }
}

function mostrarResultado(elemento, mensagem, tipo) {
    elemento.textContent = mensagem;
    elemento.classList.remove("error", "success");
    if (tipo) {
        elemento.classList.add(tipo);
    }
}
