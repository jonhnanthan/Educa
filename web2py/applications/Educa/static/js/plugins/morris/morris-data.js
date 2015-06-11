// Morris.js Charts sample data for SB Admin template

$(function() {

    // Area Chart
    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2010 Q1',
            Aluno1: 1,
            Aluno2: 1,
            Aluno3: 1
        }, {
            period: '2010 Q2',
            Aluno1: 1,
            Aluno2: 0,
            Aluno3: 0
        }, {
            period: '2010 Q3',
            Aluno1: 0,
            Aluno2: 0,
            Aluno3: 0
        }, {
            period: '2010 Q4',
            Aluno1: 0,
            Aluno2: 1,
            Aluno3: 1
        }, {
            period: '2011 Q1',
            Aluno1: 1,
            Aluno2: 1,
            Aluno3: 1
        }, {
            period: '2011 Q2',
            Aluno1: 1,
            Aluno2: 1,
            Aluno3: 1
        }, {
            period: '2011 Q3',
            Aluno1: 1,
            Aluno2: 1,
            Aluno3: 1
        }, {
            period: '2011 Q4',
            Aluno1: 1,
            Aluno2: 1,
            Aluno3: 1
        }, {
            period: '2012 Q1',
            Aluno1: 0,
            Aluno2: 0,
            Aluno3: 0
        }, {
            period: '2012 Q2',
            Aluno1: 1,
            Aluno2: 1,
            Aluno3: 0
        }],
        xkey: 'period',
        ykeys: ['Aluno1', 'Aluno2', 'Aluno3'],
        labels: ['Aluno1', 'Aluno2', 'Aluno3'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });

    // Donut Chart
    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Exec Responde 1",
            value: 12
        }, {
            label: "Exec Responde 2",
            value: 30
        }, {
            label: "Exec Responde 3",
            value: 20
        }],
        resize: true
    });

    // Line Chart
    Morris.Line({
        // ID of the element in which to draw the chart.
        element: 'morris-line-chart',
        // Chart data records -- each entry in this array corresponds to a point on
        // the chart.
        data: [{
            d: '2012-10-01',
            Tentativas: 802
        }, {
            d: '2012-10-02',
            Tentativas: 783
        }, {
            d: '2012-10-03',
            Tentativas: 820
        }, {
            d: '2012-10-04',
            Tentativas: 839
        }, {
            d: '2012-10-05',
            Tentativas: 792
        }, {
            d: '2012-10-06',
            Tentativas: 859
        }, {
            d: '2012-10-07',
            Tentativas: 790
        }, {
            d: '2012-10-08',
            Tentativas: 1680
        }, {
            d: '2012-10-09',
            Tentativas: 1592
        }, {
            d: '2012-10-10',
            Tentativas: 1420
        }, {
            d: '2012-10-11',
            Tentativas: 882
        }, {
            d: '2012-10-12',
            Tentativas: 889
        }, {
            d: '2012-10-13',
            Tentativas: 819
        }, {
            d: '2012-10-14',
            Tentativas: 849
        }, {
            d: '2012-10-15',
            Tentativas: 870
        }, {
            d: '2012-10-16',
            Tentativas: 1063
        }, {
            d: '2012-10-17',
            Tentativas: 1192
        }, {
            d: '2012-10-18',
            Tentativas: 1224
        }, {
            d: '2012-10-19',
            Tentativas: 1329
        }, {
            d: '2012-10-20',
            Tentativas: 1329
        }, {
            d: '2012-10-21',
            Tentativas: 1239
        }, {
            d: '2012-10-22',
            Tentativas: 1190
        }, {
            d: '2012-10-23',
            Tentativas: 1312
        }, {
            d: '2012-10-24',
            Tentativas: 1293
        }, {
            d: '2012-10-25',
            Tentativas: 1283
        }, {
            d: '2012-10-26',
            Tentativas: 1248
        }, {
            d: '2012-10-27',
            Tentativas: 1323
        }, {
            d: '2012-10-28',
            Tentativas: 1390
        }, {
            d: '2012-10-29',
            Tentativas: 1420
        }, {
            d: '2012-10-30',
            Tentativas: 1529
        }, {
            d: '2012-10-31',
            Tentativas: 1892
        }, ],
        // The name of the data record attribute that contains x-visitss.
        xkey: 'd',
        // A list of names of data record attributes that contain y-visitss.
        ykeys: ['Tentativas'],
        // Labels for the ykeys -- will be displayed when you hover over the
        // chart.
        labels: ['Tentativas'],
        // Disables line smoothing
        smooth: false,
        resize: true
    });

    // Bar Chart
    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            exec: 'exec',
            numacertos: 20
        }, {
            exec: 'exec 3G',
            numacertos: 10
        }, {
            exec: 'exec 3GS',
            numacertos: 15
        }, {
            exec: 'exec 4',
            numacertos: 15
        }, {
            exec: 'exec 4S',
            numacertos: 54
        }, {
            exec: 'exec 5',
            numacertos: 28
        }],
        xkey: 'exec',
        ykeys: ['numacertos'],
        labels: ['Geekbench'],
        barRatio: 0.4,
        xLabelAngle: 35,
        hideHover: 'auto',
        resize: true
    });


});
