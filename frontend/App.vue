<script>

const dataToSend =

export default {
    form: {
        username: "",
        email: "",
        password: "",
    }

    data() {
        return {
            
        };
    },
    methods: {
        sendData() {
            if (this.username != "" && this.username != "" && this.username != "") {
                fetch("http://localhost:8181/api/v1/register", {
                    method: "POST",
                    body: form
                })
                    .then(response => console.log(response))
                console.log(form)
            }
        },
    },
}

</script>

<template>

    <input type="text" v-model="username" placeholder="Имя" />
    <input type="password" v-model="password" placeholder="Пароль" />
    <input type="email" v-model="email" placeholder="Email" />
    <button @click="sendData()">Зарегистрировать</button>

</template>

<style scoped></style>
