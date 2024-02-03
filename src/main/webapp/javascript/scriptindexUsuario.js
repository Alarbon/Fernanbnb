window.onload = function () {
  //Boton de busqueda alojamiento
  const botonAbrirModal = document.getElementById("abrirModal");
  const botonCerrarModal = document.getElementById("cerrarModal");
  const modal = document.getElementById("miModal");
  const modal2 = document.getElementById("miModal2");

  botonAbrirModal.addEventListener("click", (event) => {
    // Evita que el clic en el botón se propague al evento de clic en la ventana
    event.stopPropagation();
    modal.classList.remove("hidden");
    modal2.classList.remove("hidden");
  });

  botonCerrarModal.addEventListener("click", () => {
    modal.classList.add("hidden");
    modal2.classList.add("hidden");
  });

  // Cierra el modal cuando se hace clic en cualquier lugar de la página, excepto en el modal.
  window.addEventListener("click", (event) => {
    if (event.target !== modal && event.target === modal2) {
      modal.classList.add("hidden");
      modal2.classList.add("hidden");
    }
  });

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

  //Modal Inicio Sesion
  const botonAbrirInicioSesion = document.getElementById("abrirInicioSesion");
  const botonCerrarInicioSesion = document.getElementById("cerrarInicioSesion");
  const modalInicioSesion = document.getElementById("modalInicioSesion");
  const modalInicioSesion2 = document.getElementById("modalInicioSesion2");

  botonAbrirInicioSesion.addEventListener("click", (event) => {
    // Evita que el clic en el botón se propague al evento de clic en la ventana
    event.stopPropagation();
    modalInicioSesion.classList.remove("hidden");
    modalInicioSesion2.classList.remove("hidden");
  });

  botonCerrarInicioSesion.addEventListener("click", () => {
    modalInicioSesion.classList.add("hidden");
    modalInicioSesion2.classList.add("hidden");
  });

  // Cierra el modal cuando se hace clic en cualquier lugar de la página, excepto en el modal.
  window.addEventListener("click", (event) => {
    if (
      event.target !== modalInicioSesion &&
      event.target === modalInicioSesion2
    ) {
      modalInicioSesion.classList.add("hidden");
      modalInicioSesion2.classList.add("hidden");
    }
  });

  //Modal Registrarse
  const botonAbrirRegistro = document.getElementById("abrirRegistro");
  const botonCerrarRegistro = document.getElementById("cerrarRegistro");
  const modalRegistro = document.getElementById("modalRegistro");
  const modalRegistro2 = document.getElementById("modalRegistro2");

  botonAbrirRegistro.addEventListener("click", (event) => {
    // Evita que el clic en el botón se propague al evento de clic en la ventana
    event.stopPropagation();
    modalRegistro.classList.remove("hidden");
    modalRegistro2.classList.remove("hidden");
  });

  botonCerrarRegistro.addEventListener("click", () => {
    modalRegistro.classList.add("hidden");
    modalRegistro2.classList.add("hidden");
  });

  // Cierra el modal cuando se hace clic en cualquier lugar de la página, excepto en el modal.
  window.addEventListener("click", (event) => {
    if (event.target !== modalRegistro && event.target === modalRegistro2) {
      modalRegistro.classList.add("hidden");
      modalRegistro2.classList.add("hidden");
    }
  });
};

//Validar formulario de busqueda
function validacionFormularioBusqueda() {
  const numNoches = document.getElementById("numNoches").value;
  const numViajeros = document.getElementById("numViajeros").value;

  if (numNoches <= 0 || numViajeros <= 0) {
    alert(
      "El número de noches y el número de viajeros deben ser mayores que 0."
    );
    return false;
  }

  return true;
}

document.addEventListener("DOMContentLoaded", function () {
  const registroForm = document.getElementById("registroForm");
  const mensajeError = document.getElementById("mensajeError");

  registroForm.addEventListener("submit", function (event) {
    // Detiene el envío del formulario
    event.preventDefault();

    // Obtén los valores de los campos
    const nombreUsuario = document.getElementById("nombreUsuario").value;
    const correoUsuario = document.getElementById("correoUsuario").value;
    const contrasena = document.getElementById("contrasenaRegistro").value;
    const confirmarContrasena = document.getElementById(
      "confirmarContrasena"
    ).value;

    // Valida que todos los campos estén completos
    if (
      !nombreUsuario ||
      !correoUsuario ||
      !contrasena ||
      !confirmarContrasena
    ) {
      mensajeError.textContent = "Todos los campos son obligatorios";
      return;
    }

    // Valida que el correo sea válido
    const correoValido = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(correoUsuario);
    if (!correoValido) {
      mensajeError.textContent = "Correo electrónico no válido";
      return;
    }

    // Valida que las contraseñas sean iguales
    if (contrasena.length < 4) {
      mensajeError.textContent = "La contraseña debe tener al menos 4 caracteres";
      return;
  }
    // Valida que las contraseñas coincidan
    if (contrasena !== confirmarContrasena) {
      mensajeError.textContent = "Las contraseñas no coinciden";
      return;
    }

    // Si pasa todas las validaciones, puedes enviar el formulario
    registroForm.submit();

    // Redirige con una marca de tiempo
    window.location.href = "validacionRegistro.jsp?nombreUsuario=" + nombreUsuario +
        "&correoUsuario=" + correoUsuario + "&contrasenaRegistro=" + contrasena;

  });
});
