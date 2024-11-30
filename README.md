<h1 align="center">GA7-220501096-AA5-EV02 API </h1>

>
<p align="left">
 DESCRIPCIÓN

✅ Servicio web para registro y un inicio de sesión


<p/>
<br>

<h2>Requisitos</h2>

**1. Tener instalado Java jdk 21**

**2. Tener instalado MySQL y Maven**

<h2>Configuración 📌</h2>

**1. Clonar la aplicación**

```bash
git clone https://github.com/juan-jcr/api.git
cd api
```

**2. Crear una base de datos api_sena**

**3. Editar la configuración application.properties para conectarse a la base de datos**

**4. Correr la aplicación**

<br>

<h2>Funcionalidad 🔎</h2>


##### <a id="signup">Sign Up -> /auth/sign-up</a>
```json
{
  "username":"juan",
  "password":"password"
}
```

##### <a id="signin">Log In -> /auth/log-in</a>
```json
{
  "username":"juan",
  "password":"password"
}
```

