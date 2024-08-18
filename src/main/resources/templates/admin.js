document.addEventListener('DOMContentLoaded', () => {
    // Функция для загрузки пользователей
    function loadUsers() {
        fetch('/api/users')
            .then(response => response.json())
            .then(users => {
                const tableBody = document.getElementById('users-table-body');
                const modalsContainer = document.getElementById('modals-container');
                tableBody.innerHTML = '';
                modalsContainer.innerHTML = '';

                users.forEach(user => {
                    // Создаем строку для таблицы
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.age}</td>
                        <td>${user.username}</td>
                        <td>${user.roles.map(role => role.name).join(', ')}</td>
                        <td><button type="button" class="btn btn-primary" style="background-color: #17a2b8; border: none" data-toggle="modal" data-target="#staticBackdropEdit-${user.id}">Edit</button></td>
                        <td><button type="button" class="btn btn-danger" style="border: none" data-toggle="modal" data-target="#staticBackdropDelete-${user.id}">Delete</button></td>
                    `;
                    tableBody.appendChild(row);

                    // Создаем модальное окно для редактирования
                    createEditModal(user);

                    // Создаем модальное окно для удаления
                    createDeleteModal(user);
                });
            })
            .catch(error => console.error('Ошибка при загрузке пользователей:', error));
    }

    // Функция для создания модального окна редактирования
    function createEditModal(user) {
        // Код для создания модального окна редактирования
    }

    // Функция для создания модального окна удаления
    function createDeleteModal(user) {
        // Код для создания модального окна удаления
    }

    // Функция для сохранения изменений пользователя
    function saveUser(userId) {
        // Код для сохранения изменений пользователя
    }

    // Функция для удаления пользователя
    function deleteUser(userId) {
        // Код для удаления пользователя
    }

    loadUsers(); // Начальная загрузка списка пользователей
});