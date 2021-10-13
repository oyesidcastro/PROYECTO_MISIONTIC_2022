//const url = "https://minticloud.uis.edu.co/c3s56formador/api/clientes"
const url = "http://localhost:8080/Tutorial/api/clientes";
const contenedor = document.querySelector('tbody')
let resultados = ''


const modalClientes = new bootstrap.Modal(document.getElementById('modalCliente'))
const formClientes = document.querySelector('form')
const nombreCliente = document.getElementById('nombre')
const direccionCliente = document.getElementById('direccion')
const telefonoCliente = document.getElementById('telefono')
const emailCliente = document.getElementById('ciudad')
const idCliente = document.getElementById('id')

let opcion = ''

btnCrear.addEventListener('click', () => {
    nombreCliente.value = ''
    direccionCliente.value = ''
    telefonoCliente.value = ''
    emailCliente.value = ''
    idCliente.value = ''
    idCliente.disabled = false
    modalClientes.show()
    opcion = 'crear'
})


const ajax = (options) => {
    let { url, method, success, error, data } = options;
    const xhr = new XMLHttpRequest();

    xhr.addEventListener("readystatechange", (e) => {
        if (xhr.readyState !== 4) return;

        if (xhr.status >= 200 && xhr.status < 300) {
            let json = JSON.parse(xhr.responseText);
            success(json);
        } else {
            let message = xhr.statusText || "Ocurrio un error";
            error(`Error ${xhr.status}: ${message}`);
        }
    });

    xhr.open(method || "GET", url);
    xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
    xhr.send(JSON.stringify(data));
};

/*MUESTRA LA TABLA CON TODA LA INFORMACION*/

const getAll = () => {
    ajax({
        url: url,
        success: (res) => {
            console.log(res);

            res.forEach((clientes) => {
                resultados += `<tr>
                        <td width="10%">${clientes.codigo}</td>
                        <td width="15%">${clientes.nombres}</td>
                        <td width="25%">${clientes.direccion}</td>
                        <td width="15%">${clientes.telefono}</td>
                        <td width="15%">${clientes.email}</td>
                        <td class="text-center" width="20%"><a class="btnEditar btn btn-primary">Editar</a><a class="btnBorrar btn btn-danger">Borrar</a></td>
                    </tr>`
            });

            contenedor.innerHTML = resultados
        },
        error: (err) => {
            console.log(err);
            $table.insertAdjacentHTML("afterend", `<p><b>${err}</b></p>`);
        },
    });
};


/*eliminaun registro*/
document.addEventListener("DOMContentLoaded", getAll);
document.addEventListener("click", (e) => {

    if (e.target.matches(".btnBorrar")) {
        const fila = e.target.parentNode.parentNode
        const id = fila.firstElementChild.innerHTML
        console.log(id)
        alertify.confirm(`Está seguro de eliminar el Código ${id}?`,
            function () {
                ajax({
                    url: url + "/" + id,
                    method: "DELETE",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    success: (res) => location.reload(),
                    error: (err) => alert(err),
                });
                alertify.success('Registro eliminado')
            },
            function () {
                alertify.error('Cancel')
            });



    }


//datos a editar

    if (e.target.matches(".btnEditar")) {
        const fila = e.target.parentNode.parentNode
        idCliente.value = fila.children[0].innerHTML
        nombreCliente.value = fila.children[1].innerHTML
        direccionCliente.value = fila.children[2].innerHTML
        telefonoCliente.value = fila.children[3].innerHTML
        emailCliente.value = fila.children[4].innerHTML
        idCliente.disabled = true
        opcion = 'editar'
        modalClientes.show()
    }
})


//EDITA EL PROVEEDOR

formClientes.addEventListener('submit', (e) => {
    e.preventDefault()
    let metodo = "POST"
    if (opcion == 'editar') {
        metodo = "PUT"

    }
    ajax({
        url: url,
        method: metodo,
        headers: {
            'Content-Type': 'application/json'
        },
        success: (res) => location.reload(),
        error: (err) =>
            $form.insertAdjacentHTML("afterend", `<p><b>${err}</b></p>`),
        data: {
            "email": emailCliente.value,
            "direccion": direccionCliente.value,
            "codigo": idCliente.value,
            "nombres": nombreCliente.value,
            "telefono": telefonoCliente.value
        },
    });
    modalClientes.hide()
})
