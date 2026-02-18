package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.*;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=4fc7c187";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSerie = new ArrayList<>();
    public void muestraElMenu(){
        var opcion = -1;
                while(opcion != 0){
                        var menu = """
                                1 - Buscar Series
                                2 - Buscar Episodios
                                3 - Mostrar series buscadas
                                0 - Salir
                                        """;
                        System.out.println(menu);
                        opcion = teclado.nextInt();
                        teclado.nextLine();

                        switch (opcion) {
                                case 1:
                                        buscarSerieWeb();
                                        break;
                                case 2:
                                        buscarEpisodiosPorSerie();
                                        break;
                                case 0:
                                        System.out.println("Cerrando la aplicacion...");
                                        break;
                                case 3:
                                        mostrarSeriesBuscadas();
                                        break;                        
                                default:
                                        System.out.println("Opci�n invalida");
                                        break;
                        }

                }
    }
    
    private DatosSerie getDatosSerie(){
        System.out.println("Escribe el nombre de la série que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        //System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    //Busca los datos de todas las temporadas
    private void buscarEpisodiosPorSerie(){
        DatosSerie datosSerie = getDatosSerie();
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datosSerie.totalTemporadas(); i++) {
            var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&Season=" + i + API_KEY);
            DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void buscarSerieWeb(){
        DatosSerie datos = getDatosSerie();
        datosSerie.add(datos);
        System.out.println(datos);
    }

    //Busca los datos generales de las series
    private void mostrarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = datosSerie.stream()
                .map(d -> new Serie(d))
                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
        }


        //temporadas.forEach(System.out::println);

        

        //Mostrar solo el titulo de los episodios para las temporadas
        // for (int i = 0; i < datos.totalTemporadas(); i++) {
        //     List<DatosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();
        //     for (int j = 0; j < episodiosTemporadas.size(); j++) {
        //         System.out.println(episodiosTemporadas.get(j).titulo());
        //     }
        // }

        // Mejoría usando funciones Lambda
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        // List<DatosEpisodio> datosEpisodios = temporadas.stream()
        //         .flatMap(t -> t.episodios().stream())
        //         .collect(Collectors.toList());

        // Obtener los top 5 episodios
        // System.out.println("\n Top 5 episodios");
        // datosEpisodios.stream()
        //         .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
        //         .peek(e -> System.out.println("Primer filtro (N/A)" + e))
        //         .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
        //         .peek(e -> System.out.println("Segunda ordenacion (M>m)" + e))
        //         .map(e -> e.titulo().toUpperCase())
        //         .peek(e -> System.out.println("Tercer filtro may�scula (m>M)" + e))
        //         .limit(5)

        //         .forEach(System.out::println);

        //Convirtiendo los datos a una lista del tipo Episodio
        // List<Episodio> episodios = temporadas.stream()
                // .flatMap(t -> t.episodios().stream()
                //         .map(d -> new Episodio(t.numero(), d)))
                // .collect(Collectors.toList());

        //episodios.forEach(System.out::println);

        // Busqueda de episodios a partir de x año
        // System.out.println("a partir de que año deseas ver los episodios?");
        // var fecha = teclado.nextInt();
        // teclado.nextLine();

        //LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);

        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // episodios.stream()
        //         .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
        //         .forEach(e -> System.out.println(
        //                 "Temporada: " + e.getTemporada() +
        //                         " Episodio: " + e.getTitulo() +
        //                         " Fecha de Lanzamiento: " + e.getFechaDeLanzamiento().format(dtf)
        //         ));

        //Busca episodios por pedazos de titulo
        // System.out.println("Por favor escriba el titulo que desea ver");
        // var pedazoTitulo = teclado.nextLine();
        // Optional<Episodio> episodioBuscado = episodios.stream()
        //         .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
        //         .findFirst();
        // if (episodioBuscado.isPresent()){
        //         System.out.println("Episodio Encontrado!!");
        //         System.out.println("Los datos son: " + episodioBuscado.get());
        // }else {
        //         System.out.println("Episodio no encontrado");
        // }

        // Map<Integer,Double> evaluacionesPorTemporada = episodios.stream()
        //         .filter(e -> e.getEvaluacion() > 0.0)
        //         .collect(Collectors.groupingBy(Episodio::getTemporada,
        //                 Collectors.averagingDouble(Episodio::getEvaluacion)));
        // System.out.println(evaluacionesPorTemporada);

        // DoubleSummaryStatistics est = episodios.stream()
        //                 .filter(e -> e.getEvaluacion() > 0.0)
        //                 .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        // System.out.println("La media de las evaluaciones: " + est.getAverage());
        // System.out.println("El episodio mejor evaluado: " + est.getMax());
        // System.out.println("Episodio peor evaluado: " + est.getMin());
//     }
}
