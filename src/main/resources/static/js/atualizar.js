function atualizar() {

  const nome = document.getElementById("nomeEditavel").value.trim();
  const prontuario = document.getElementById("prontuarioEditavel").innerHTML.trim();
  const msgBoxEdicao = document.getElementById("resultadoEdicao");

  msgBoxEdicao.innerHTML = '';

  if (!nome || !prontuario || !pacienteAtualId) {
    msgBoxEdicao.innerHTML = `<p class="erro">⚠️ Nome e prontuário são obrigatórios.</p>`;
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
      msgBoxEdicao.classList.add("resultado-visivel");
      msgBoxEdicao.innerHTML = `<p class="sucesso">✅ Paciente <b>${paciente.nome}</b> atualizado com sucesso!</p>`;
    })
    .catch(err => {
      msgBoxEdicao.innerHTML = `<p class="erro">❌ Falha ao atualizar.</p>`;
    });
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