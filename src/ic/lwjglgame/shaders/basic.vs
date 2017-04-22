#version 330

layout(location = 0) in vec3 vert;
layout(location = 1) in vec2 vertUV;
layout(location = 2) in vec3 vertNormal;

out vec2 uv;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main()
{
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vert, 1);
	uv = vertUV;
}