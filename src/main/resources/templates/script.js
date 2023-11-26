
// Создаем объект XMLHttpRequest
const xhr = new XMLHttpRequest();

const button = document.querySelector('button.submit_btn');

button.addEventListener('click', () => {


   fetch("http://localhost:8080/form", {
     responseType: "text",
   })
     .then((response) => {
       if (response.ok) {
         // Успешный запрос
         document.write(response.text);
       } else {
         // Ошибка запроса
       }
     })
     .catch((error) => {
       // Ошибка запроса
     });

});
