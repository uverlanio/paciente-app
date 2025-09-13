function buscar() {
  const termo = document.getElementById("busca").value.trim();
  const msgBox = document.getElementById("resultado");

  if (!termo) {
    msgBox.innerHTML = `<p class="erro">⚠️ Digite um ID ou nome para buscar.</p>`;
    return;
  }

  fetch(`/api/pacientes/${termo}`)
    .then(res => {
      if (!res.ok) {
        throw new Error("Paciente não encontrado");
      }
      return res.json();
    })
    .then(data => {
      msgBox.innerHTML = `
        <h3>${data.nome}</h3>
        <p>${data.prontuario}</p>
        <small>Última atualização: ${new Date(data.ultimaAtualizacao).toLocaleString()}</small>
      `;
    })
    .catch(err => {
      msgBox.innerHTML = `<p class="erro">❌ ${err.message}</p>`;
    });
}

function criar() {
  const nome = document.getElementById("nomeNovo").value.trim();
  const prontuario = document.getElementById("prontuarioNovo").value.trim();
  const msgBox = document.getElementById("resultado");

  if (!nome || !prontuario) {
    msgBox.innerHTML = `<p class="erro">⚠️ Nome e prontuário são obrigatórios.</p>`;
    return;
  }

  fetch("/api/pacientes", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, prontuario })
  })
    .then(res => {
      if (!res.ok) {
        return res.text().then(textoErro => {
          throw new Error(textoErro || "Erro ao salvar paciente");
        });
      }
      return res.json();
    })
    .then(paciente => {
      msgBox.innerHTML = `<p class="sucesso">✅ Paciente <b>${paciente.nome}</b> criado com sucesso!</p>`;
      document.getElementById("nomeNovo").value = "";
      document.getElementById("prontuarioNovo").value = "";
    })
    .catch(err => {
      msgBox.innerHTML = `<p class="erro">❌ Não foi possível salvar paciente.<br>${err.message}</p>`;
    });
}
