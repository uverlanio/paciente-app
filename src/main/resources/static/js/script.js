let pacienteAtualId = null;

function buscar() {
  const termo = document.getElementById("busca").value.trim();
  const msgBox = document.getElementById("resultado");
  const edicaoBox = document.getElementById("edicaoPaciente");

  // Oculta a div #resultado por padrão antes de iniciar a busca
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

function login() {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const msgBox = document.getElementById("message");

    msgBox.innerHTML = '';
    msgBox.style.display = 'none';
    msgBox.classList.remove("error", "success");

    if (!username || !password) {
        msgBox.innerHTML = '⚠️ Usuário e senha são obrigatórios.';
        msgBox.classList.add("error");
        msgBox.style.display = 'block';
        return;
    }

    fetch("/api", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            nome: username,
            senha: password
        })
    })
    .then(res => {
        // If the response is a success (status 2xx), return the response itself.
        // We will not try to parse JSON from it.
        if (res.ok) {
            return res;
        }

        // If the response is not a success, we'll try to get the error message text.
        // This avoids the "Unexpected end of JSON input" error on failed logins.
        return res.text().then(text => {
            throw new Error(text || "Erro ao fazer login. Tente novamente.");
        });
    })
    .then(() => {
        // If we reach this block, the login was successful.
        // The previous .then() already handled the HTTP status code.
        msgBox.classList.add("success");
        msgBox.style.display = 'block';
        window.location.href = "home.html";
    })
    .catch(err => {
        // This catches any error, whether it's from the network, the API, or the promise chain.
        window.location.href = "login.html";
        msgBox.classList.add("error");
        msgBox.style.display = 'block';
    });
}