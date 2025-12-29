window.pacienteAtualId = null;

function buscar() {

  const termo = document.getElementById("busca").value.trim();
  const msgBox = document.getElementById("resultado");
  const edicaoBox = document.getElementById("edicaoPaciente");

  msgBox.innerHTML = '';
  msgBox.style.display = 'none';

  if (!termo) {
    edicaoBox.style.display = "none";
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
      pacienteAtualId = data.id;
      msgBox.style.display = 'block';
      msgBox.innerHTML = `
        <small class='alinharDataAtualizacao'>Última atualização: ${new Date(data.ultimaAtualizacao).toLocaleString()}</small>
      `;

      document.getElementById("nomeEditavel").value = data.nome;
      preencherProntuario(data.prontuario);
      edicaoBox.style.display = "block";
    })
    .catch(err => {
      msgBox.style.display = 'block';
      msgBox.innerHTML = `<p class="erro">❌ Falha ao buscar.</p>`;
      edicaoBox.style.display = "none";
      setTimeout(() => {
                  window.location.href = "criar.html";
              }, 3000);
    });
}