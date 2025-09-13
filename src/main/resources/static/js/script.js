function buscar() {
  const termo = document.getElementById("busca").value;
  fetch(`/api/pacientes/${termo}`)
    .then(res => res.json())
    .then(data => {
      document.getElementById("resultado").innerHTML = `
        <h3>${data.nome}</h3>
        <p>${data.prontuario}</p>
        <small>Última atualização: ${new Date(data.ultimaAtualizacao).toLocaleString()}</small>
      `;
    });
}

function criar() {
  const nome = document.getElementById("nomeNovo").value;
  const prontuario = document.getElementById("prontuarioNovo").value;

  fetch("/api/pacientes", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, prontuario })
  })
  .then(res => res.json())
}