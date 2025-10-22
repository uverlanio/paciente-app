function criar() {
  const nome = document.getElementById("nomeNovo").value.trim();
  const prontuario = document.getElementById("prontuarioNovo").value.trim();
  const msgBox = document.getElementById("resultado");

  msgBox.innerHTML = '';
  msgBox.classList.remove("resultado-visivel");

  if (!nome || !prontuario) {
    msgBox.innerHTML = `<p class="erro">⚠️ Nome e prontuário são obrigatórios.</p>`;
    msgBox.classList.add("resultado-visivel");
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
          throw new Error(textoErro || "Erro ao criar paciente");
        });
      }
      return res.json();
    })
    .then(paciente => {

      msgBox.classList.add("resultado-visivel");
      msgBox.innerHTML = `<p class="sucesso">✅ Paciente <b>${paciente.nome}</b> criado com sucesso!</p>`;

      document.getElementById("nomeNovo").value = "";
      document.getElementById("prontuarioNovo").value = "";
    })
    .catch(err => {
      msgBox.classList.add("resultado-visivel");
      msgBox.innerHTML = `<p class="erro">❌ Falha ao criar.</p>`;
    });
}