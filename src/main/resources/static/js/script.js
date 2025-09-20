let pacienteAtualId = null;

function buscar() {
  const termo = document.getElementById("busca").value.trim();
  const msgBox = document.getElementById("resultado");
  const edicaoBox = document.getElementById("edicaoPaciente");

  // Oculta a div #resultado por padrão antes de iniciar a busca
  msgBox.innerHTML = '';
  msgBox.style.display = 'none';

  if (!termo) {
    //msgBox.style.display = 'block'; // Mostra a div apenas para a mensagem de erro
    //msgBox.innerHTML = `<p class="erro">⚠️ Digite um ID ou nome para buscar.</p>`;
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
      msgBox.style.display = 'block'; // Mostra a div #resultado com o conteúdo
      msgBox.innerHTML = `
        <small class='alinharDataAtualizacao'>Última atualização: ${new Date(data.ultimaAtualizacao).toLocaleString()}</small>
      `;

      // Preenche os campos de edição
      document.getElementById("nomeEditavel").value = data.nome;
      document.getElementById("prontuarioEditavel").value = data.prontuario;
      edicaoBox.style.display = "block";
    })
    .catch(err => {
      msgBox.style.display = 'block'; // Mostra a div #resultado com a mensagem de erro
      msgBox.innerHTML = `<p class="erro">❌ ${err.message}</p>`;
      edicaoBox.style.display = "none";
    });
}

function atualizar() {
  const nome = document.getElementById("nomeEditavel").value.trim();
  const prontuario = document.getElementById("prontuarioEditavel").value.trim();
  const msgBoxEdicao = document.getElementById("resultadoEdicao"); // Nova referência

  // Limpa a caixa de mensagem antes de uma nova atualização
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
      msgBoxEdicao.innerHTML = `<p class="erro">❌ Falha ao atualizar.<br>${err.message}</p>`;
    });
}

function criar() {
  const nome = document.getElementById("nomeNovo").value.trim();
  const prontuario = document.getElementById("prontuarioNovo").value.trim();
  const msgBox = document.getElementById("resultado");

  // Limpa a caixa de mensagem e remove a classe de estilo
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
      // Adiciona a classe e exibe a mensagem de sucesso
      msgBox.classList.add("resultado-visivel");
      msgBox.innerHTML = `<p class="sucesso">✅ Paciente <b>${paciente.nome}</b> criado com sucesso!</p>`;
      // Limpar os campos após a criação
      document.getElementById("nomeNovo").value = "";
      document.getElementById("prontuarioNovo").value = "";
    })
    .catch(err => {
      // Adiciona a classe e exibe a mensagem de erro
      msgBox.classList.add("resultado-visivel");
      msgBox.innerHTML = `<p class="erro">❌ Falha ao criar.<br>${err.message}</p>`;
    });
}