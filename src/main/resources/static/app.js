document.addEventListener('DOMContentLoaded', () => {

    const loginScreen = document.getElementById('login-screen');
    const appScreen   = document.getElementById('app-screen');
    const loginForm   = document.getElementById('login-form');
    const loginError  = document.getElementById('login-error');

    // ---------- Логин ----------
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        loginError.classList.add('hidden');

        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value;

        if (!username || !password) {
            showError('Заполните оба поля');
            return;
        }

        try {
            const res  = await fetch('/api/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });
            const data = await res.json();

            if (data.success) {
                loginScreen.classList.add('hidden');
                appScreen.classList.remove('hidden');
                document.getElementById('welcome-title').textContent = data.message;
            } else {
                showError(data.message);
            }
        } catch {
            showError('Сервер недоступен');
        }
    });

    // ---------- Выход ----------
    document.getElementById('logout-btn').addEventListener('click', () => {
        appScreen.classList.add('hidden');
        loginScreen.classList.remove('hidden');
        loginForm.reset();
    });

    // ---------- Загрузка данных (оставшийся функционал) ----------
    document.getElementById('load-btn').addEventListener('click', async () => {
        const res  = await fetch('/api/hello');
        const data = await res.json();
        document.getElementById('result').textContent = data.message;
    });

    // ---------- Утилита ----------
    function showError(msg) {
        loginError.textContent = msg;
        loginError.classList.remove('hidden');
    }
});