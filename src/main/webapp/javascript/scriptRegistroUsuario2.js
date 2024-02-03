document.addEventListener("DOMContentLoaded", function () {
    const registroForm = document.getElementById("registroFormUsuarios");
    const mensajeError = document.getElementById("mensajeError");

    registroForm.addEventListener("submit", function (event) {
        // Detiene el envío del formulario
        event.preventDefault();
        // Obtén los valores de los campos
        const nombreUsuario = document.getElementById("nombreUsuario").value;
        const correoUsuario = document.getElementById("correoUsuario").value;
        const contrasena = document.getElementById("contrasenaRegistro").value;
        const confirmarContrasena = document.getElementById("confirmarContrasena").value;

        // Valida que todos los campos estén completos
        if (!nombreUsuario || !correoUsuario || !contrasena || !confirmarContrasena) {
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
        registroFormUsuarios.submit();

        // Redirige con una marca de tiempo
        window.location.href = "validacionRegistro.jsp?nombreUsuario=" + nombreUsuario +
            "&correoUsuario=" + correoUsuario + "&contrasenaRegistro=" + contrasena;

    });
});
  