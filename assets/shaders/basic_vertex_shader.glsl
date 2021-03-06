#version 300 es

precision mediump float;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture_position;

out vec2 pass_texture_position;

void main()
{
	
	pass_texture_position = texture_position;
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1.0);
}
