#version 460

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;

out vec3 oNormal;
out vec4 gl_Position;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;

void main() {
    oNormal = normal;
    gl_Position = vec4(position, 1.0);
}
