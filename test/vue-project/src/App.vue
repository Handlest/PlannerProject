<script>

export default {
    data() {
        return {
            username: "",
            email: "",
            password: "",
        };
    },
    methods: {
        sendData() {
            if (this.username != "" && this.username != "" && this.username != "") {
                fetch("http://localhost:8181/api/v1/auth/register", {
                    method: 'POST',
                    headers:{'content-type': 'application/json'},
                    body: JSON.stringify({
                        "username": this.username,
                        "email": this.email,
                        "password": this.password
                    })
                }).then((response) => {
                    if (!response.ok){
                        throw Error(response.statusText);
                    }
                    console.log('responce: ', response.json())
                } )
                
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

<style scoped>

</style>
