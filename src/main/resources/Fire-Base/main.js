import { onAuthStateChanged } from "https://www.gstatic.com/firebasejs/10.4.0/firebase-auth.js"
import { auth } from "./validador/firebase.js";
import './validador/signupForm.js'
import './validador/signinForm.js'
import './validador/googleLogin.js'

onAuthStateChanged(auth, async (user) => {
    if(user) {
        console.log(user)
    } else {
        console.log('No hay nadie logueado')
    }
});
