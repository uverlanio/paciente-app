function atualizar() {

  const nome = document.getElementById("nomeEditavel").value.trim();
  const prontuario = document.getElementById("prontuarioEditavel").innerHTML.trim();
  const msgBoxEdicao = document.getElementById("resultadoEdicao");

  msgBoxEdicao.innerHTML = '';
  msgBoxEdicao.classList.remove("error", "success");

  if (!nome || !prontuario || !pacienteAtualId) {
    msgBoxEdicao.textContent = "Nome e prontuário são obrigatórios.";
    msgBoxEdicao.classList.add("error");
    return;
  }

  fetch(`/api/pacientes/${pacienteAtualId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, prontuario })
  })
    .then(res => {
      if (!res.ok) {
        return res.text().then(textoErro => {
          throw new Error(textoErro || "Erro ao atualizar paciente");
        });
      }
      return res.json();
    })
    .then(paciente => {
      msgBoxEdicao.textContent = `Prontuário de ${paciente.nome} atualizado com sucesso.`;
      msgBoxEdicao.classList.add("success");
    })
    .catch(err => {
      msgBoxEdicao.textContent = err.message || "Não foi possível atualizar o prontuário.";
      msgBoxEdicao.classList.add("error");
    });
}

async function excluir() {
  const nome = document.getElementById("nomeEditavel").value.trim();
  if (!pacienteAtualId || !window.confirm(`Deseja excluir permanentemente o prontuário de ${nome}?`)) {
    return;
  }

  const resultado = document.getElementById("resultadoEdicao");
  resultado.textContent = "";
  resultado.classList.remove("error", "success");

  try {
    const response = await fetch(`/api/pacientes/${encodeURIComponent(pacienteAtualId)}`, {
      method: "DELETE"
    });
    if (!response.ok) {
      throw new Error("Não foi possível excluir o prontuário. Tente novamente.");
    }

    document.getElementById("edicaoPaciente").hidden = true;
    pacienteAtualId = null;
    mostrarResultadoBusca(document.getElementById("resultado"), "Prontuário excluído com sucesso.", "success");
  } catch (error) {
    resultado.textContent = error.message || "Não foi possível excluir o prontuário.";
    resultado.classList.add("error");
  }
}

function execComando(event, comando) {
    event.preventDefault();
    document.execCommand(comando, false, null);
    document.getElementById("prontuarioEditavel").focus();
}

function preencherProntuario(htmlContent) {
    const editor = document.getElementById("prontuarioEditavel");
    if (htmlContent) {
        editor.innerHTML = htmlContent;
    } else {
        editor.innerHTML = '';
    }
}
