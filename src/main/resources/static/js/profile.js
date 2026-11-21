function initializeProfile() {
    const loginData = readLoginStatus();
    const profileNames = document.querySelectorAll("[data-profile-name]");
    const initials = document.querySelector("[data-profile-initials]");
    const image = document.querySelector("[data-profile-image]");
    const fileInput = document.querySelector("[data-profile-file]");
    const selectButton = document.querySelector("[data-profile-select]");
    const feedback = document.querySelector("[data-profile-feedback]");

    if (!loginData) {
        return;
    }
    if (!image) {
        return;
    }

    const nome = loginData.nome || "Profissional";
    profileNames.forEach(element => {
        element.textContent = nome;
    });
    if (initials) {
        initials.textContent = criarIniciais(nome);
    }

    carregarFoto();

    if (!fileInput || !selectButton) {
        return;
    }

    selectButton.addEventListener("click", () => fileInput.click());
    fileInput.addEventListener("change", enviarFoto);

    async function enviarFoto() {
        const foto = fileInput.files[0];
        if (!foto) {
            return;
        }
        if (!["image/jpeg", "image/png"].includes(foto.type) || foto.size > 5 * 1024 * 1024) {
            mostrarMensagem("Escolha uma imagem JPEG ou PNG de até 5 MB.", true);
            fileInput.value = "";
            return;
        }

        const dados = new FormData();
        dados.append("foto", foto);
        selectButton.disabled = true;
        mostrarMensagem("Enviando foto...", false);

        try {
            const response = await fetch("/api/perfil/foto", {
                method: "POST",
                body: dados
            });
            if (response.status === 401) {
                encerrarSessao();
                return;
            }
            if (!response.ok) {
                const detalhe = await response.text();
                throw new Error(detalhe || "Não foi possível atualizar a foto.");
            }

            mostrarMensagem("Foto de perfil atualizada.", false);
            await carregarFoto();
        } catch (error) {
            mostrarMensagem(error.message || "Não foi possível conectar. Tente novamente.", true);
        } finally {
            selectButton.disabled = false;
            fileInput.value = "";
        }
    }

    async function carregarFoto() {
        try {
            const response = await fetch("/api/perfil/foto", { cache: "no-store" });
            if (response.status === 401) {
                encerrarSessao();
                return;
            }
            if (response.status === 404) {
                return;
            }
            if (!response.ok) {
                throw new Error("Não foi possível carregar a foto do perfil.");
            }

            const foto = await response.blob();
            if (image.src.startsWith("blob:")) {
                URL.revokeObjectURL(image.src);
            }
            image.src = URL.createObjectURL(foto);
            image.hidden = false;
        } catch (error) {
            mostrarMensagem(error.message || "Não foi possível carregar a foto do perfil.", true);
        }
    }

    function mostrarMensagem(texto, erro) {
        if (!feedback) {
            return;
        }
        feedback.textContent = texto;
        feedback.classList.toggle("error", erro);
    }
}

document.addEventListener("DOMContentLoaded", initializeProfile);
window.addEventListener("auth-ready", initializeProfile);

function readLoginStatus() {
    try {
        return JSON.parse(localStorage.getItem("loginStatus"));
    } catch (error) {
        localStorage.removeItem("loginStatus");
        return null;
    }
}

function criarIniciais(nome) {
    return nome
        .trim()
        .split(/\s+/)
        .slice(0, 2)
        .map(parte => parte.charAt(0).toLocaleUpperCase("pt-BR"))
        .join("") || "P";
}

function encerrarSessao() {
    localStorage.removeItem("loginStatus");
    window.location.href = "login.html";
}
