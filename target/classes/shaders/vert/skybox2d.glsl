#version 450

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

out vec2 aTexCoord;
out vec4 gl_Position;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;
uniform vec2 offset;

void main() {
    aTexCoord = offset + texCoord;
    gl_Position = model * vec4(position, 1.0);
}
