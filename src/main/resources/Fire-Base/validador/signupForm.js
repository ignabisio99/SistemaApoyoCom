import { createUserWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-auth.js"
import { auth } from "./firebase.js";
import { showMessage } from "./showMessage.js";

const signUpForm = document.querySelector("#signup-form");

signUpForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = signUpForm["signup-email"].value;
    const password = signUpForm["signup-password"].value;

    try {
        const userCredential = await createUserWithEmailAndPassword(auth, email, password)

        // Close the signup modal
        const signupModal = document.querySelector('#signupModal');
        const modal = bootstrap.Modal.getInstance(signupModal);
        modal.hide();

        // reset the form
        signUpForm.reset();

        showMessage("Se ha registrado exitosamente " + userCredential.user.email, "success");

    } catch (error) {
        console.log(error)
        if (error.code === 'auth/email-already-in-use') {
            showMessage("El correo electrónico ya fue usado", "error")
        } else if (error.code === 'auth/invalid-email') {
            showMessage("Correo electrónico invalido", "error")
        } else if (error.code === 'auth/weak-password') {
            showMessage("Contraseña débil", "error")
        } else if (error.code) {
            showMessage("Algo salió mal", "error")
        }
    }

});