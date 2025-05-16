// Task - 1

const button = document.getElementById('add-item-btn');

button.addEventListener('click', function () {
    const notification = document.createElement('div');
    notification.className = 'notification';
    notification.innerHTML = `
        <p>Это уведомление</p>
        <button>Закрыть</button>
    `;

    document.body.appendChild(notification);

    const closeBtn = notification.querySelector('button');
    closeBtn.addEventListener('click', () => {
        notification.remove();
    });

    setTimeout(() => {
        notification.remove();
    }, 5000);
});

// Task - 2
