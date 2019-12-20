#version 460

layout(location = 0) in vec3 position;

out vec4 gl_Position;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;

void main() {
    gl_Position = vec4(position, 1.0);
}
