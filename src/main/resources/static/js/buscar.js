window.pacienteAtualId = null;

async function buscar() {
    const termo = document.getElementById("busca").value.trim();
    const msgBox = document.getElementById("resultado");
    const edicaoBox = document.getElementById("edicaoPaciente");

    mostrarResultadoBusca(msgBox, "");
    edicaoBox.hidden = true;
    window.pacienteAtualId = null;

    if (!termo) {
        mostrarResultadoBusca(msgBox, "Informe o ID ou o nome do paciente.", "error");
        return;
    }

    try {
        const response = await fetch(`/api/pacientes/${encodeURIComponent(termo)}`);
        if (response.status === 404) {
            throw new Error("Nenhum prontuário encontrado para essa busca.");
        }
        if (!response.ok) {
            throw new Error("Não foi possível buscar o prontuário. Tente novamente.");
        }

        const paciente = await response.json();
        window.pacienteAtualId = paciente.id;
        const atualizado = paciente.ultimaAtualizacao || "Data indisponível";
        mostrarResultadoBusca(msgBox, `Última atualização: ${atualizado}`, "success");
        document.getElementById("nomeEditavel").value = paciente.nome;
        preencherProntuario(paciente.prontuario);
        document.getElementById("resultadoEdicao").textContent = "";
        document.getElementById("resultadoEdicao").classList.remove("error", "success");
        edicaoBox.hidden = false;
    } catch (error) {
        mostrarResultadoBusca(msgBox, error.message || "Não foi possível conectar. Tente novamente.", "error");
    }
}

function mostrarResultadoBusca(elemento, mensagem, tipo) {
    elemento.textContent = mensagem;
    elemento.classList.remove("error", "success");
    if (tipo) {
        elemento.classList.add(tipo);
    }
}
