const TABLE_BODY = document.querySelector('#javascript-content tbody');

fetch('/authorized')
    .then(response => {return response.json()})
    .then(authorizedUser => {
        // console.log(authorizedUser)
        document.querySelector('.header-message').textContent = authorizedUser.email + ' with roles: ' + authorizedUser.rolesString;
    })


fetch('/admin/users')
.then(response => {return response.json()})
.then(data => {
    let userList = Array.from(data)
    userList.forEach(user => createTableRow(user))
    alert('Загружена страница')
})

function createTableRow (user) {
    let row = `<tr id="user-id-${user.id}">
                    <td>${user.id}</td>
                    <td class="important">${user.firstName}</td>
                    <td class="important">${user.lastName}</td>
                    <td class="important">${user.age}</td>
                    <td class="important">${user.email}</td>
                    <td class="unknown" id="authorities">${user.rolesString}</td>
                        
                    <td class="unknown">
                        <a class="btn btn-sm btn-info"
                        role="button"
                        modal-view-selector="#user-edition-modal"
                        row-selector="#user-id-${user.id} td:not(.unknown)"
                        onclick="displayModalView(this)">
                        <span class="text-white">Edit</span>
                        </a>
                    </td>

                    <td class="unknown">
                        <a class="btn btn-sm btn-danger"
                        role="button"
                        modal-view-selector="#user-deletion-modal"
                        row-selector="#user-id-${user.id} td:not(.unknown)"
                        onclick="displayModalView(this)">
                        <span class="text-white">Delete</span>
                        </a>
                    </td>
              </tr>`;
    TABLE_BODY.insertAdjacentHTML('beforeend', row);
}

function displayModalView(button) {
    let userData = getUserData(button);
    let modalView = document.querySelector(button.getAttribute('modal-view-selector'))
    let formInputs = Array.from(modalView.querySelectorAll('input:not(#edition-form-password)'))
    for (let i = 0; i < userData.length; i++) {
        formInputs[i].setAttribute('value', userData[i])
    }
    console.log('Действует обработчик открытия модального окна')
    let modalElement = new bootstrap.Modal(modalView)
    modalElement.show();
}

function closeButton () {
    let formView = document.querySelector('#user-edition-modal form');
    formView.reset();
    if (formView.querySelector('.validation-message').hidden === false){
        validateAction(formView, 'hide')
    }
}

function getUserData (button) {
    let nodeList = Array.from(document.querySelectorAll(button.getAttribute('row-selector')));
    let userData = [];
    for (let node of nodeList) {
        userData.push(node.textContent)
    }
    return userData;
}

function deleteRow (event, id) {
    event.preventDefault()
    fetch(`/admin/users/${id}`, {method: 'DELETE'})
        .then(response => {
            if (response.ok) {
                let deletedUserRow = document.querySelector(`#javascript-content tbody #user-id-${id}`)
                deletedUserRow.remove();
            }
        })
    $('#user-deletion-modal').modal('hide')
}

async function userSubmitAction(event, form, userID) {
    event.preventDefault()
    console.log('Выполняется обработчик Сохранение пользователя')
    let formData = new FormData(form)
    let userData = {
        id: userID,
        firstName: formData.get('firstName').trim(),
        lastName: formData.get('lastName').trim(),
        age: formData.get('age'),
        email: formData.get('email').trim(),
        password: formData.get('password').trim(),
        roles: formData.getAll('roleName')
    };

    const isPostAction = userID === '';

    let httpMethod = 'PUT'

    if (isPostAction) {
        delete userData.id
        httpMethod = 'POST'
    }

    let goodResponse;
    let primaryKey = await fetch(`/admin/users/${userID}`, {
        method: httpMethod,
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(userData)
    }).then(response => {
        goodResponse = response.ok
        return response.text();
    })
    let isDisplayedValidMessage;
    if (!goodResponse) {
        validateAction(form, 'show')
        isDisplayedValidMessage = true;

        function emailInputValueListener() {
            if (this.value === '') {
                validateAction(form, 'hide')
                isDisplayedValidMessage = false
                this.removeEventListener('input', emailInputValueListener)
            }
        }
        form.email.addEventListener('input', emailInputValueListener);
        return
    }
    console.log('Продолжается')
    let roleNamesWithoutPrefix = [];
    userData.roles.forEach(roleName => {
        roleNamesWithoutPrefix.push(roleName.substring(5))
    })
    userData.rolesString = roleNamesWithoutPrefix.join(' ');
    if (isPostAction) {
        userData.id = primaryKey;
        createTableRow(userData);
        form.reset();
        let anotherTabLink = document.querySelector('#admin-panel a[href="#users-table"]')
        bootstrap.Tab.getInstance(anotherTabLink).show()
    } else {
        let userEditionRow = document.querySelector(`#javascript-content tbody #user-id-${userID}`)
        let nodeListToUpdate = Array.from(userEditionRow.querySelectorAll('.important, #authorities'))
        let formDataValues = Array.from(formData.values())
        for (let i = 0; i < 4; i++) {
            nodeListToUpdate[i].textContent = formDataValues[i]
        }
        nodeListToUpdate[4].textContent = userData.rolesString;
        $('#user-edition-modal').modal('hide')
    }
    if (isDisplayedValidMessage) validateAction(form, 'hide')
}

function validateAction (form, action) {
    const elem = form.querySelector('.validation-message');

    if (action === 'hide') {elem.setAttribute('hidden', 'hidden')}
    else {elem.removeAttribute('hidden')}

    // elem.textContent = action === 'hide' ? '' : 'Указанный email занят'
    if (action === 'hide') {form.email.classList.remove('border-danger')}
    else {form.email.classList.add('border-danger')}
}









//
//         })
// }
//






