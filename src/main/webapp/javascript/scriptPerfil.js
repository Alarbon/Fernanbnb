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
document.addEventListener("DOMContentLoaded", function () {
  // Botón "Editar Perfil"
  const perfilSection = document.getElementById("perfil");
  const editarPerfilBtn = document.getElementById("editarPerfil");
  const editarPerfilForm = document.getElementById("editarPerfilForm");
  const perfilForm = document.getElementById("perfilForm");
  const nuevaContrasena = document.getElementById("nuevaContrasena");
  const repetirContrasena = document.getElementById("repetirContrasena");
  const nuevoCorreo = document.getElementById("nuevoCorreo");
  const error = document.getElementById("error");

  // Mostrar el formulario al hacer clic en "Editar Perfil"
  editarPerfilBtn.addEventListener("click", function () {
    perfilSection.style.display = "none";
    editarPerfilForm.style.display = "block";
  });

  // Validación de correo
  perfilForm.addEventListener("submit", function (event) {
    const correoValue = nuevoCorreo.value;
    const correoRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!correoRegex.test(correoValue)) {
      event.preventDefault(); // Evitar que el formulario se envíe
      error.textContent = "Por favor, ingresa una dirección de correo válida.";
      error.classList.remove("hidden");

      return;
    }
  });

  // Validación de contraseñas iguales
  perfilForm.addEventListener("submit", function (event) {
    const contrasenaValue = nuevaContrasena.value;
    const repetirContrasenaValue = repetirContrasena.value;

    if (contrasenaValue !== repetirContrasenaValue) {
      event.preventDefault(); // Evitar que el formulario se envíe
      error.textContent = "Las contraseñas no coinciden.";
      error.classList.remove("hidden");

      return;
    }
  });
});
