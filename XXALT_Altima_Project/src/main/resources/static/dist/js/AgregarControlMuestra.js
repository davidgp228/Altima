//Agregar procreso
 function agregar() {
      if (document.getElementById("id_familia_prenda").value &&
    		  document.getElementById("nombre_prenda").value &&
    		  document.getElementById("precio").value &&
    		  document.getElementById("cantidad").value &&
    		  document.getElementById("talla").value &&
    		  document.getElementById("largo").value ) {
		      var familia=document.getElementById("id_familia_prenda").value;
		      var nombre=document.getElementById("nombre_prenda").value;
		      var precio=document.getElementById("precio").value;
		      var cantidad=document.getElementById("cantidad").value;
		      var talla=document.getElementById("talla").value;
		      var largo=document.getElementById("largo").value;
		      var idPedido=document.getElementById("idPedido").value;
		      
    	 $.ajax({
    	method: "POST",
        url: "/guardar-prenda-foranea",
        data: { 
        	 "_csrf": $('#token').val(),
        	'familia': familia,
        	'nombre': nombre,
        	'precio': precio,
        	'cantidad': cantidad, 
        	'talla': talla,
        	"largo":largo,
        	'idPedido':idPedido
        },
        success: (data) => {
			document.getElementById("idPedido").value =data;
			
			listarMarcas(data);
		} 
    })
    .done(function( data ) { 	
    	});
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Insertado correctamente',
          showConfirmButton: false,
          timer: 1250
        })
        
        document.getElementById("id_familia_prenda").value = '';
  		$('#id_familia_prenda').change();
	    document.getElementById("nombre_prenda").value ='';
	    document.getElementById("precio").value='';
	    document.getElementById("cantidad").value='';
	    document.getElementById("talla").value='';
	    document.getElementById("largo").value='';
    	
        
      }else{
    	  Swal.fire({
    		  position: 'center',
	          icon: 'error',
	          title: 'Debe completar todo el formulario',
	          showConfirmButton: false,
	          timer: 1250
            })
      }
}
 
//Dar de orden
 function bajarOrden(idbaja) {
	 
	 console.log(idbaja);
 	var id = idbaja;
 	Swal.fire({
 		title: '¿Deseas dar de baja orden?',
 		icon: 'warning',
 		showCancelButton: true,
 		cancelButtonColor: '#6C757D',
 		cancelButtonText: 'Cancelar',
 		confirmButtonText: 'Dar de baja',
 		confirmButtonColor: '#17a2b8',
 	}).then((result) => {
 		if (result.value && id != null) {
 			$.ajax({
 				type: "POST",
 				url: "/bajaorden",
 				data: {
 					"_csrf": $('#token').val(),
 					'id': id

 					// ,'Descripcion':Descripcion
 				}

 			}).done(function (data) {

 				document.getElementById("idPedido").value =data;
 				
 				listarMarcas(data);
 			});
 			Swal.fire({
 				position: 'center',
 				icon: 'success',
 				title: 'dado de baja correctamente',
 				showConfirmButton: false,
 				timer: 1250
 			})
 		} //////////////termina result value
 	})
 }
 // LISTAR
 function listarMarcas(id) {
		$.ajax({
			method: "GET",
			url: "/prenda-ordenes/"+id,
			success: (data) => {
				$('#quitar').remove();
				$('#contenedorTabla').append(
					"<div class='modal-body' id='quitar'>" +
					"<table class='table table-striped table-bordered' style='width:100%' id='idtable'>" +
					"<thead>" +
					"<tr>" +
					"<th>Prenda</th>" +
					"<th>Modelo</th>" +
					"<th>Costo</th>" +
					"<th>Cantidad</th>" +
					
					"<th>Talla</th>" +
					"<th>Largo</th>" +
					"<th>Eliminar</th>" +
					"</tr>" +
					"</thead>" +
					"</table>"+ "</div>" );
				var a;
				var b = [];
				for (i in data) {

					a = [
						"<tr>" +
						"<td>" + data[i][0]+ "</td>",
						"<td>" + data[i][1]+ "</td>",
						"<td>" + data[i][2]+ "</td>",
						"<td>" + data[i][3]+ "</td>",
						"<td>" + data[i][4]+ "</td>",
						"<td>" + data[i][5]+ "</td>",
						"<td style='text-align: center;'> <button  onclick='bajarOrden(" + data[i][6] + ")'  class='btn btn-danger btn-circle btn-sm'> <i class='fas fa-minus'></i></button></td>",
						"<tr>"
					];
					b.push(a);
				}
				var tablaMarcas = $('#idtable').DataTable({
					"data": b,
					"ordering": true,
					"pageLength": 5,
					"responsive": true,
					"lengthMenu": [
						[5, 10, 25, 50, 100],
						[5, 10, 25, 50, 100]
					],
					"language": {
						"sProcessing": "Procesando...",
						"sLengthMenu": "Mostrar _MENU_ registros",
						"sZeroRecords": "No se encontraron resultados",
						"sEmptyTable": "Ningún dato disponible en esta tabla =(",
						"sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
						"sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
						"sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
						"sInfoPostFix": "",
						"sSearch": "Buscar:",
						"sUrl": "",
						"sInfoThousands": ",",
						"sLoadingRecords": "Cargando...",
						"oPaginate": {
							"sFirst": "Primero",
							"sLast": "Último",
							"sNext": "Siguiente",
							"sPrevious": "Anterior"
						},
						"oAria": {
							"sSortAscending": ": Activar para ordenar la columna de manera ascendente",
							"sSortDescending": ": Activar para ordenar la columna de manera descendente"
						},
						"buttons": {
							"copy": "Copiar",
							"colvis": "Visibilidad"
						}
					}
				});
				
			},
			error: (e) => {
		
			}
		})
	}