function logoff() {
    localStorage.removeItem("loginStatus");
    const redirecionar = () => {
        window.location.href = "login.html";
    };

    fetch("/api/perfil/logout", { method: "POST" }).then(redirecionar, redirecionar);
}
