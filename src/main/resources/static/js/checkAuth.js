function checkAuth() {
    const statusString = localStorage.getItem('loginStatus');

    if (!statusString) {
        window.location.href = 'login.html';
        return;
    }

    const loginData = JSON.parse(statusString);
    const currentTime = Date.now();

    if (currentTime > loginData.expiresAt) {

        localStorage.removeItem('loginStatus');
        window.location.href = 'login.html';
    }
}
