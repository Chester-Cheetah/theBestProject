const TABLE_BODY = document.querySelector('#javascript-content tbody');
const EDITION_FORM = document.querySelector('.user-form-edition')
const EDITION_MODAL = document.querySelector('#user-edition-modal')
const DELETION_MODAL = document.querySelector('#user-deletion-modal')
const NEW_USER_FORM = document.querySelector('#user-submit-form')

let isDisplayedValidMessage;

fetch('/authorized')
    .then(response => {
        return response.json()
    })
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

fetch('/admin/users')
    .then(response => {
        return response.json()
    })
    .then(data => {
        let userList = Array.from(data)
        userList.forEach(user => createTableRow(user))
    })

function createTableRow(user) {
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
    let modalElement = new bootstrap.Modal(modalView)
    modalElement.show();
}

function closeButton() {
    EDITION_FORM.reset();
    if (isDisplayedValidMessage) {
        validateAction(EDITION_FORM, 'hide')
    }
}

function getUserData(button) {
    let nodeList = Array.from(document.querySelectorAll(button.getAttribute('row-selector')));
    let userData = [];
    for (let node of nodeList) {
        userData.push(node.textContent)
    }
    return userData;
}

function deleteRow(event, id) {
    event.preventDefault()
    fetch(`/admin/users/${id}`, {method: 'DELETE'})
        .then(response => {
            if (response.ok) {
                let deletedUserRow = TABLE_BODY.querySelector(`#user-id-${id}`)
                deletedUserRow.remove();
            }
        })
    $(DELETION_MODAL).modal('hide')
}

let triggerTabList = [].slice.call(document.querySelectorAll('.nav-link'))
triggerTabList.forEach(function (triggerEl) {
    let tabTrigger = new bootstrap.Tab(triggerEl)

    triggerEl.addEventListener('click', function (event) {
        event.preventDefault()
        tabTrigger.show()
        if (this !== triggerTabList[3]) {
            validateAction(NEW_USER_FORM, 'hide')
            NEW_USER_FORM.reset();
        }
    })
})

async function userSubmitAction(event, form, userID) {
    event.preventDefault()
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
    if (!goodResponse) {
        validateAction(form, 'show')
        form.email.addEventListener('input', emailInputValueListener);

        function emailInputValueListener() {
            if (this.value === '') {
                validateAction(form, 'hide')
                this.removeEventListener('input', emailInputValueListener)
            }
        }
        return
    }
    let roleNamesWithoutPrefix = [];
    userData.roles.forEach(roleName => {
        roleNamesWithoutPrefix.push(roleName.substring(5))
    })
    userData.rolesString = roleNamesWithoutPrefix.join(' ');
    if (isPostAction) {
        userData.id = primaryKey;
        createTableRow(userData);
        let anotherTabLink = document.querySelector('#admin-panel a[href="#users-table"]')
        bootstrap.Tab.getInstance(anotherTabLink).show()
    } else {
        let userEditionRow = TABLE_BODY.querySelector(`#user-id-${userID}`)
        let nodeListToUpdate = Array.from(userEditionRow.querySelectorAll('.important, #authorities'))
        let formDataValues = Array.from(formData.values())
        for (let i = 0; i < 4; i++) {
            nodeListToUpdate[i].textContent = formDataValues[i]
        }
        nodeListToUpdate[4].textContent = userData.rolesString;
        $(EDITION_MODAL).modal('hide')
    }
    form.reset();
    if (isDisplayedValidMessage) validateAction(form, 'hide')
}

function validateAction(form, action) {
    let elem;
    if (form.id === "user-submit-form") {
        elem = document.querySelector('#new-user-validation-message')
    } else {
        elem = document.querySelector('#edition-validation-message')
    }

    if (action === 'hide') {
        elem.textContent = ''
        form.email.classList.remove('border-danger')
        isDisplayedValidMessage = false;
    } else {
        elem.textContent = 'Указанный email занят'
        form.email.classList.add('border-danger')
        isDisplayedValidMessage = true;
    }
}