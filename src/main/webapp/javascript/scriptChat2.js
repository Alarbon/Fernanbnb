window.onload = function () {
  //Menu de profile
  const profileButton = document.getElementById("profileButton");
  const profileMenu = document.getElementById("profileMenu");

  profileButton.addEventListener("click", () => {
    event.stopPropagation();
    if (profileMenu.classList.contains("hidden")) {
      // Si el modal está oculto, lo mostramos
      profileMenu.classList.remove("hidden");
    } else {
      // Si el modal está visible, lo ocultamos
      profileMenu.classList.add("hidden");
    }
  });

  // Cierra el modal cuando se hace clic fuera del modal
  window.addEventListener("click", (event) => {
    if (event.target !== profileButton && event.target !== profileMenu) {
      profileMenu.classList.add("hidden");
    }
  });
  // Evita que los clics dentro del modal se propaguen y cierren el modal
  profileMenu.addEventListener("click", (event) => {
    event.stopPropagation();
  });


};

// Cuando la página se carga, haz scroll hacia abajo
window.addEventListener("load", function () {
  const messageContainer = document.getElementById("message-container");
  messageContainer.scrollTop = messageContainer.scrollHeight;
});


function confirmDelete(idMensaje, idUsuarioHaBorrado) {
  Swal.fire({
    title: "¿Estás seguro de que deseas borrar este mensaje para ti?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Sí, borrarlo",
    cancelButtonText: "Cancelar",
  }).then((result) => {
    if (result.isConfirmed) {
      // El usuario confirmó, redirecciona a la página JSP de borrarMensaje.jsp
      window.location.href = "borrarMensaje.jsp?idMensaje=" + idMensaje + "&idUsuarioHaBorrado=" + idUsuarioHaBorrado;
    } else {
      // El usuario canceló, no hagas nada o realiza otra acción si es necesario
      Swal.fire("Cancelado", "El mensaje no ha sido borrado.", "info");
    }
  });
}
  // Agrega un listener para el evento submit del formulario
  const chatForm = document.getElementById("chat-form2");
  chatForm.addEventListener("submit", function (event) {
    const messageInput = document.getElementById("message-input");
    const message = messageInput.value.trim(); // Obtener el valor del mensaje y eliminar espacios en blanco al principio y al final

    // Verificar si el mensaje tiene más de 100 caracteres
    if (message.length > 100) {
      alert("El mensaje no puede superar los 100 caracteres.");
      event.preventDefault(); // Evitar el envío del formulario si el mensaje es demasiado largo
    }
  });

