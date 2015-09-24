var COUNT_OPACITY = 0.5;

function generateCounts(data) {

	areasLayer.selectAll("circle").data(data).enter().append("circle") //
	.attr("class", "point") //
	.attr("startTime", function(d) {

		return (d.startTime);

	}) //
	.attr("endTime", function(d) {

		return (d.endTime);

	}) //
	.attr(
			"cx",
			function(d) {

				var xy = projection([ d.location.coordinate.yCoordinate,
						d.location.coordinate.xCoordinate ]);
				var cx = xy[0]; // lat

				return (cx);
			}) //
	.attr(
			"cy",
			function(d) {

				var xy = projection([ d.location.coordinate.yCoordinate,
						d.location.coordinate.xCoordinate ]);
				var cy = xy[1]; // long

				return (cy);
			}) //
	.attr("r", function(d) {

		var count = d.attributes.count;

		// TODO: map them
		var radius = 10 * Math.sqrt(count / Math.PI);

		return (radius);

	}) //
	.attr("fill", "brown") //
	.attr("fill-opacity", COUNT_OPACITY) //
	.attr("stroke", "#fff") //
	.attr("stroke-width", "0.5px");

}// END: generateCounts

function generatePoints(data) {

	var points = pointsLayer.selectAll("circle").data(data).enter().append(
			"circle") //
	.attr("class", "point") //
	.attr("startTime", function(d) {

		return (d.startTime);

	}) //
	.attr(
			"cx",
			function(d) {

				var xy = projection([ d.location.coordinate.yCoordinate,
						d.location.coordinate.xCoordinate ]);
				var cx = xy[0]; // lat

				return (cx);
			}) //
	.attr(
			"cy",
			function(d) {

				var xy = projection([ d.location.coordinate.yCoordinate,
						d.location.coordinate.xCoordinate ]);
				var cy = xy[1]; // long

				return (cy);
			}) //
	.attr("r", "5px") //
	.attr("fill", "white") //
	.attr("stroke", "black");

	// dump attribute values into DOM
	points[0].forEach(function(d, i) {

		var thisPoint = d3.select(d);
		var properties = data[i].attributes;

		for ( var property in properties) {
			if (properties.hasOwnProperty(property)) {

				thisPoint.attr(property, properties[property]);

			}
		}// END: properties loop
	});

}// END: generatePoints