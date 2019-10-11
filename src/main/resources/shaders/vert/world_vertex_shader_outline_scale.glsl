#version 450

layout(location = 0) in vec3 position;

uniform mat4 model;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

out vec4 gl_Position;

void main() {
    gl_Position = projection * view * model * vec4(position, 1.0);
}
