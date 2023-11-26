
// Создаем объект XMLHttpRequest
const xhr = new XMLHttpRequest();

const button = document.querySelector('button.submit_btn');

button.addEventListener('click', () => {


   if (xhr.readyState === 4 && xhr.status === 200) {
      // Получаем ответ от сервера
      const response = xhr.responseText;

      // Обрабатываем ответ от сервера
      // В этом случае мы просто выводим ответ в браузер
      document.querySelector("body").innerHTML = response;
   }

   // Настраиваем параметры запроса
   xhr.open("GET", "http://localhost:8080/form");

   // Отправляем запрос
   xhr.send();

});
