function atualizarModelos() {
    console.log("atualizar modelosStatic...")
    var marcaSelecionada = parseInt(document.getElementById("marca").value);
    console.log("marca.." + marcaSelecionada)
    var modelosSelect = document.getElementById("modelo");

    // Limpa as opções atuais do select de modelos
    modelosSelect.innerHTML = '<option value="">Selecione o modelo</option>';

    // Fazer uma requisição AJAX para obter os modelos de carro correspondentes à marca selecionada
    fetch('/buscarPorMarca?marca=' + parseInt(marcaSelecionada)) //modelosPorMarca
        .then(response => response.json())
        .then(data => {
            data.forEach(function(modelo) {
                var option = document.createElement("option");
                option.value = modelo.id;
                option.text = modelo.modelo;
                modelosSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao obter modelos de carro:', error));
}