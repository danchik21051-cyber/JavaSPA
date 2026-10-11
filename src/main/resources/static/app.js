document.addEventListener('DOMContentLoaded', () => {

    const loginScreen = document.getElementById('login-screen');
    const appScreen   = document.getElementById('app-screen');
    const loginForm   = document.getElementById('login-form');
    const loginError  = document.getElementById('login-error');
    let username_value;

    // ---------- Логин ----------
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        loginError.classList.add('hidden');

        username_value = document.getElementById('username').value.trim();
        const password_value = document.getElementById('password').value;

        if (!username_value || !password_value) {
            showError('Заполните оба поля');
            return;
        }

        try {
            const res  = await fetch('/api/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username: username_value, password: password_value })
            });
            const data = await res.json();

            if (data.success) {
                loginScreen.classList.add('hidden');
                appScreen.classList.remove('hidden');
                document.getElementById('welcome-title').textContent = data.message;
                currentUser = username_value;
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
        document.getElementById('result').textContent = "";
        loginScreen.classList.remove('hidden');
        loginForm.reset();
    });

    // ---------- Показать сообщение ----------
    document.getElementById('load-btn').addEventListener('click', async () => {
        const res  = await fetch('/api/hello');
        const data = await res.json();
        document.getElementById('result').textContent = data.message+", "+username_value;
    });

    // ---------- Утилита ----------
    function showError(msg) {
        loginError.textContent = msg;
        loginError.classList.remove('hidden');
    }
});