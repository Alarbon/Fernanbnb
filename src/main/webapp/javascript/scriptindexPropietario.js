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
