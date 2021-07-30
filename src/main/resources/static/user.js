fetch('/authorized')
    .then(response => {return response.json()})
    .then(authorizedUser => {
        let userDataArray = [
            authorizedUser.id,
            authorizedUser.firstName,
            authorizedUser.lastName,
            authorizedUser.age,
            authorizedUser.email,
            authorizedUser.rolesString
        ]
        document.querySelector('.header-message').textContent = authorizedUser.email + ' with roles: ' + authorizedUser.rolesString;
        const tableRow = Array.from(document.querySelectorAll('#q td'))
        for (let i = 0; i < tableRow.length; i++) {
            tableRow[i].textContent = userDataArray[i]
        }
    })