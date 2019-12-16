#version 450

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform vec3 uColor;
uniform mat4 model;

out vec3 oColor;
out vec3 aPosition;

void main() {
    oColor = uColor;
    gl_Position = projection * view * model * vec4(position, 1.0);
    aPosition = (model * vec4(position, 1.0)).xyz;
}
