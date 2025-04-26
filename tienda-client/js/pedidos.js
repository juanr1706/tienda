import { validateTokenOrRedirect, getAccessToken, logout } from './auth.js';

let camisasCache = [];
let totalPedido = 0;

validateTokenOrRedirect();

async function listarPedidos() {
    try {
        const response = await fetch("http://localhost:8080/pedidos", {
            headers: {
                "Authorization": `Bearer ${getAccessToken()}`
            }
        });

        if (response.ok) {
            const pedidos = await response.json();
            const pedidosList = document.getElementById("pedidosList");
            pedidosList.innerHTML = ""; // Limpiar la lista antes de agregar nuevos elementos

            pedidos.forEach(pedido => {
                const li = document.createElement("li");
                li.classList.add("list-group-item");
                li.innerHTML = `
                    Pedido ID: ${pedido.idpedido} - Cliente: ${pedido.cliente.nombre} - Fecha: ${pedido.fechapedido} - Total: $${pedido.total}
                    <button class="btn btn-danger btn-sm float-end ms-2 eliminar-btn" data-idpedido="${pedido.idpedido}">Eliminar</button>
                    <button class="btn btn-primary btn-sm float-end" data-bs-toggle="modal" data-bs-target="#detalleModal" data-idpedido="${pedido.idpedido}">
                        Ver Detalles
                    </button>
                `;
                pedidosList.appendChild(li);
            });

            // Añadir el eventListener a los botones
            const botonesDetalles = document.querySelectorAll('button[data-idpedido]');
            botonesDetalles.forEach(boton => {
                boton.addEventListener('click', function() {
                    const idpedido = boton.getAttribute('data-idpedido');
                    verDetalles(idpedido);
                });
            });

            const botonesEliminar = document.querySelectorAll('.eliminar-btn');
            botonesEliminar.forEach(boton => {
                boton.addEventListener('click', function () {
                    const idpedido = boton.getAttribute('data-idpedido');
                    eliminarPedido(idpedido);
                });
            });

        } else {
            alert("No se pudieron obtener los pedidos.");
        }
    } catch (error) {
        alert("Ocurrió un error al obtener los pedidos. " + error.message);
    }
}


function verDetalles(idpedido) {

    console.log("Ver detalles del pedido: " + idpedido);
    const detallePedidoBody = document.getElementById("detallePedidoBody");
    
    // Aquí deberías realizar una petición para obtener los detalles de ese pedido, por ejemplo:
    fetch(`http://localhost:8080/pedidos/${idpedido}`, {
        headers: {
            "Authorization": `Bearer ${getAccessToken()}`
        }
    })
    .then(response => response.json())
    .then(pedido => {
        const detalles = pedido.detalles.map(detalle => {
            return `
                <div>
                    <p><strong>Producto:</strong> ${detalle.camisa.nombre}</p>
                    <p><strong>Cantidad:</strong> ${detalle.cantidad}</p>
                    <p><strong>Subtotal:</strong> $${detalle.subtotal}</p>
                    <p><strong>Descripción:</strong> ${detalle.camisa.descripcion}</p>
                    <p><strong>Talla:</strong> ${detalle.camisa.talla.nombre}</p>
                    <p><strong>Color:</strong> ${detalle.camisa.color.nombre}</p>
                    <p><strong>Categoria:</strong> ${detalle.camisa.categoria.nombre}</p>
                    <hr>
                </div>
            `;
        }).join('');

        detallePedidoBody.innerHTML = detalles;
    })
    .catch(error => {
        detallePedidoBody.innerHTML = "<p>Error al obtener los detalles.</p>";
    });
}

function eliminarPedido(idpedido) {
    if (!confirm("¿Estás seguro de que deseas eliminar este pedido?")) return;

    fetch(`http://localhost:8080/pedidos/${idpedido}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${getAccessToken()}`
        }
    })
    .then(response => {
        if (response.ok) {
            alert("Pedido eliminado correctamente.");
            listarPedidos(); // Recargar la lista
        } else {
            alert("No se pudo eliminar el pedido.");
        }
    })
    .catch(error => {
        alert("Error al eliminar el pedido: " + error.message);
    });
}



async function cargarClientes() {
    try {
        const response = await fetch("http://localhost:8080/clientes", {
            headers: {
                "Authorization": `Bearer ${getAccessToken()}`
            }
        });

        if (response.ok) {
            const clientes = await response.json();
            const selectCliente = document.getElementById("cliente");

            // Limpiar opciones anteriores
            selectCliente.innerHTML = "<option value=''>Seleccione un cliente</option>";

            // Agregar las opciones de los clientes al select
            clientes.forEach(cliente => {
                const option = document.createElement("option");
                option.value = cliente.idcliente;
                option.textContent = `${cliente.nombre} (${cliente.telefono})`;
                selectCliente.appendChild(option);
            });
        } else {
            alert("No se pudieron cargar los clientes.");
        }
    } catch (error) {
        alert("Ocurrió un error al cargar los clientes. " + error.message);
    }
}

async function cargarCamisas() {
    if (camisasCache.length > 0) {
        // Si ya hemos cargado las camisas previamente, simplemente las usamos
        actualizarSelectCamisas();
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/camisas", {
            headers: {
                "Authorization": `Bearer ${getAccessToken()}`
            }
        });

        if (response.ok) {
            camisasCache = await response.json(); // Guardar las camisas en caché
            actualizarSelectCamisas();
        } else {
            alert("No se pudieron cargar las camisas.");
        }
    } catch (error) {
        alert("Ocurrió un error al cargar las camisas. " + error.message);
    }
}

window.onload = () => {
    listarPedidos();
    cargarClientes();
};

function actualizarSelectCamisas() {
    // Solo actualizar las opciones de los selects, sin borrar los detalles anteriores
    const selectCamisas = document.querySelectorAll(".selectCamisa");
    selectCamisas.forEach(select => {
        // Limpiar las opciones existentes solo si es necesario (si no existen previamente)
        if (select.children.length <= 1) {  // Solo borrar si no hay opciones previas
            camisasCache.forEach(camisa => {
                const option = document.createElement("option");
                option.value = camisa.idcamisa;
                option.textContent = `${camisa.nombre} - $${camisa.precio}`;
                select.appendChild(option);
            });
        }
    });
}

function agregarDetalle() {
    const detallesPedido = document.getElementById("detallesPedido");
    const detalleDiv = document.createElement("div");
    detalleDiv.classList.add("detalle-pedido");

    // Crear los encabezados para las columnas (Camisa, Cantidad, Subtotal)
    if (detallesPedido.children.length === 0) {
        const encabezadosDiv = document.createElement("div");
        encabezadosDiv.classList.add("encabezados-detalle");

        // Crear los labels para cada columna
        const labelCamisa = document.createElement("div");
        labelCamisa.textContent = "Camisa";
        encabezadosDiv.appendChild(labelCamisa);

        const labelCantidad = document.createElement("div");
        labelCantidad.textContent = "Cantidad";
        encabezadosDiv.appendChild(labelCantidad);

        const labelSubtotal = document.createElement("div");
        labelSubtotal.textContent = "Subtotal";
        encabezadosDiv.appendChild(labelSubtotal);

        // Añadir los encabezados al inicio
        detallesPedido.appendChild(encabezadosDiv);
    }

    // Crear select para las camisas
    const selectCamisa = document.createElement("select");
    selectCamisa.classList.add("form-control", "selectCamisa");
    selectCamisa.required = true;
    detalleDiv.appendChild(selectCamisa);

    // Crear input para la cantidad
    const inputCantidad = document.createElement("input");
    inputCantidad.type = "number";
    inputCantidad.classList.add("form-control");
    inputCantidad.placeholder = "Cantidad";
    inputCantidad.required = true;
    detalleDiv.appendChild(inputCantidad);

    // Crear div para mostrar el subtotal
    const subtotalDiv = document.createElement("div");
    subtotalDiv.classList.add("subtotal");
    subtotalDiv.textContent = "$0"; // Iniciar con 0
    detalleDiv.appendChild(subtotalDiv);

    // Crear input oculto para almacenar el valor del subtotal
    const hiddenSubtotalInput = document.createElement("input");
    hiddenSubtotalInput.type = "hidden";
    hiddenSubtotalInput.name = "subtotal";  // Puedes usar un array para manejar varios subtotales
    hiddenSubtotalInput.value = "0"; // Inicializar en 0
    detalleDiv.appendChild(hiddenSubtotalInput);

    // Crear botón de eliminación
    const btnEliminar = document.createElement("button");
    btnEliminar.type = "button";
    btnEliminar.classList.add("btn", "btn-danger", "btn-sm", "mt-2");
    btnEliminar.textContent = "Eliminar";
    detalleDiv.appendChild(btnEliminar);

    // Añadir el detalle al formulario
    detallesPedido.appendChild(detalleDiv);

    // Agregar evento para eliminar el detalle
    btnEliminar.addEventListener("click", function () {
        detalleDiv.remove();  // Eliminar el div del detalle
        recalcularTotal();  // Recalcular el total después de eliminar
    });

    // Cargar las camisas disponibles
    cargarCamisas();

    // Escuchar cambios en el select y calcular el subtotal
    selectCamisa.addEventListener("change", function () {
        const camisaId = selectCamisa.value;
        const cantidad = inputCantidad.value;
        if (camisaId && cantidad) {
            calcularSubtotal(camisaId, cantidad, subtotalDiv, hiddenSubtotalInput);
        }
    });

    // Escuchar cambios en la cantidad y recalcular el subtotal
    inputCantidad.addEventListener("input", function () {
        const camisaId = selectCamisa.value;
        const cantidad = inputCantidad.value;
        if (camisaId && cantidad) {
            calcularSubtotal(camisaId, cantidad, subtotalDiv, hiddenSubtotalInput);
        }
    });
}

async function calcularSubtotal(camisaId, cantidad, subtotalSpan, hiddenSubtotalInput) {
    if (!cantidad || cantidad <= 0) {
        subtotalSpan.textContent = "$0";
        hiddenSubtotalInput.value = "0";  // Si la cantidad es 0, el subtotal es 0
        recalcularTotal();  // Recalcular el total del pedido
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/camisas/${camisaId}`, {
            headers: {
                "Authorization": `Bearer ${getAccessToken()}`
            }
        });

        if (response.ok) {
            const camisa = await response.json();
            const subtotal = camisa.precio * cantidad;
            subtotalSpan.textContent = `$${subtotal.toFixed(2)}`;
            hiddenSubtotalInput.value = subtotal.toFixed(2);  // Actualizar el input oculto con el subtotal

            recalcularTotal(); // Recalcular el total del pedido
        } else {
            alert("No se pudo obtener la información de la camisa.");
        }
    } catch (error) {
        alert("Ocurrió un error al calcular el subtotal. " + error.message);
    }
}

function recalcularTotal() {
    const subtotales = document.querySelectorAll(".detalle-pedido");
    let totalPedido = 0;


    subtotales.forEach(subtotal => {
        // Obtener el valor del input oculto dentro de cada subtotal
        const hiddenSubtotalInput = subtotal.querySelector("input[type='hidden']");
        if (hiddenSubtotalInput) {
            totalPedido += parseFloat(hiddenSubtotalInput.value);
        }
    });

    // Actualizar el campo del total visible
    const totalTexto = document.getElementById("totalTexto");
    totalTexto.textContent = `Total: $${totalPedido.toFixed(2)}`;

    // Actualizar el input oculto con el total recalculado
    const totalPedidoInput = document.getElementById("totalPedidoInput");
    totalPedidoInput.value = totalPedido.toFixed(2);

    console.log("Total recalculado: " + totalPedido.toFixed(2));
}


document.getElementById('logoutButton').addEventListener('click', function () {
    logout();  // Llamamos al método logout desde auth.js
});


window.onload = () => {
    listarPedidos();
    cargarClientes();
};

document.getElementById("agregarDetalle").addEventListener("click", agregarDetalle);

document.getElementById("formCrearPedido").addEventListener("submit", function (event) {
    event.preventDefault();
    enviarPedido();
});

async function enviarPedido() {
    // Obtener los datos del formulario
    const clienteId = document.getElementById("cliente").value;
    const total = document.getElementById("totalPedidoInput").value;
    
    // Obtener los detalles del pedido (camisa, cantidad, subtotal)
    const detalles = [];
    const detallesPedidoDivs = document.querySelectorAll(".detalle-pedido");

    detallesPedidoDivs.forEach(detalle => {
        const camisaId = detalle.querySelector(".selectCamisa").value;
        const cantidad = detalle.querySelector("input[type='number']").value;
        const subtotal = detalle.querySelector("input[type='hidden']").value;

        // Asegúrate de que todos los campos estén completos antes de agregar el detalle
        if (camisaId && cantidad && subtotal) {
            detalles.push({
                idcamisa: camisaId,
                cantidad: cantidad,
                subtotal: subtotal
            });
        }
    });

    // Crear el objeto PedidoRequest
    const pedidoRequest = {
        idcliente: clienteId,
        total: total,
        detalles: detalles
    };

    try {
        const response = await fetch("http://localhost:8080/pedidos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${getAccessToken()}`
            },
            body: JSON.stringify(pedidoRequest)
        });

        if (response.ok) {
            const pedido = await response.json();
            alert("Pedido creado con éxito!");
            listarPedidos();  // Actualizar la lista de pedidos
        } else {
            alert("Error al crear el pedido.");
        }
    } catch (error) {
        alert("Ocurrió un error al crear el pedido. " + error.message);
    }
}

