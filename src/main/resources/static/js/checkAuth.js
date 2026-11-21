async function checkAuth() {
    const statusString = localStorage.getItem("loginStatus");
    let loginData;

    try {
        loginData = statusString ? JSON.parse(statusString) : null;
    } catch (error) {
        localStorage.removeItem("loginStatus");
    }

    if (loginData && loginData.loggedIn === true && Date.now() <= loginData.expiresAt) {
        return;
    }

    localStorage.removeItem("loginStatus");
    try {
        const response = await fetch("/api/perfil/sessao", { cache: "no-store" });
        if (!response.ok) {
            throw new Error("Sessão não autenticada.");
        }

        const usuario = await response.json();
        localStorage.setItem("loginStatus", JSON.stringify({
            loggedIn: true,
            nome: usuario.nome,
            expiresAt: Date.now() + (8 * 60 * 60 * 1000)
        }));
        window.dispatchEvent(new Event("auth-ready"));
    } catch (error) {
        localStorage.removeItem("loginStatus");
        window.location.href = "login.html";
    }
}
